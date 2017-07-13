package ERFAN.menuScreen.resizingGifs;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.ArrayList;

public class GifResizer {

    String fileAddress;
    File gifFile;
    int width,heigth;

    public GifResizer(String fileAddress,int width,int heigth){
        this.fileAddress = fileAddress;
        this.width = width;
        this.heigth = heigth;
        gifFile = new File(fileAddress);
    }


    public void make(){
        try{
            GifSequenceWriter gifWriter;
            File gifFile = new File(fileAddress);

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(gifFile);
            reader.setInput(imageInputStream);

            ImageOutputStream output = new FileImageOutputStream(new File("NewGif.gif"));
            gifWriter = new GifSequenceWriter(output,reader.read(0).getType(),1,true);

            int numberOfImages = reader.getNumImages(true);
            BufferedImage master = null;

            for(int i=0; i < numberOfImages; i++){
                System.out.println("image no " + i);
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for(int j=0; j<children.getLength(); j++){
                    Node nodeItem = children.item(j);
                    if(nodeItem.getNodeName().equals("ImageDescriptor")){
                        master = new BufferedImage(width, heigth, BufferedImage.TYPE_4BYTE_ABGR);
                        gifWriter.writeToSequence(master);

                    }
                }
                ImageIO.write(master, "GIF", new File( i + ".gif"));
            }

            gifWriter.close();
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageFrame[] readGif(InputStream stream) throws IOException{
        ArrayList<ImageFrame> frames = new ArrayList<ImageFrame>(2);

        ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
        reader.setInput(ImageIO.createImageInputStream(stream));

        int lastx = 0;
        int lasty = 0;

        int width = -1;
        int height = -1;

        IIOMetadata metadata = reader.getStreamMetadata();

        Color backgroundColor = null;

        if(metadata != null) {
            IIOMetadataNode globalRoot = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());

            NodeList globalColorTable = globalRoot.getElementsByTagName("GlobalColorTable");
            NodeList globalScreeDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");

            if (globalScreeDescriptor != null && globalScreeDescriptor.getLength() > 0){
                IIOMetadataNode screenDescriptor = (IIOMetadataNode) globalScreeDescriptor.item(0);

                if (screenDescriptor != null){
                    width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
                    height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
                }
            }

            if (globalColorTable != null && globalColorTable.getLength() > 0){
                IIOMetadataNode colorTable = (IIOMetadataNode) globalColorTable.item(0);

                if (colorTable != null) {
                    String bgIndex = colorTable.getAttribute("backgroundColorIndex");

                    IIOMetadataNode colorEntry = (IIOMetadataNode) colorTable.getFirstChild();
                    while (colorEntry != null) {
                        if (colorEntry.getAttribute("index").equals(bgIndex)) {
                            int red = Integer.parseInt(colorEntry.getAttribute("red"));
                            int green = Integer.parseInt(colorEntry.getAttribute("green"));
                            int blue = Integer.parseInt(colorEntry.getAttribute("blue"));

                            backgroundColor = new Color(red, green, blue);
                            break;
                        }

                        colorEntry = (IIOMetadataNode) colorEntry.getNextSibling();
                    }
                }
            }
        }

        BufferedImage master = null;
        boolean hasBackround = false;

        for (int frameIndex = 0;; frameIndex++) {
            BufferedImage image;
            try{
                image = reader.read(frameIndex);
            }catch (IndexOutOfBoundsException io){
                break;
            }

            if (width == -1 || height == -1){
                width = image.getWidth();
                height = image.getHeight();
            }

            IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
            IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
            NodeList children = root.getChildNodes();

            int delay = Integer.valueOf(gce.getAttribute("delayTime"));

            String disposal = gce.getAttribute("disposalMethod");

            if (master == null){
                master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                master.createGraphics().setColor(backgroundColor);
                master.createGraphics().fillRect(0, 0, master.getWidth(), master.getHeight());

                hasBackround = image.getWidth() == width && image.getHeight() == height;

                master.createGraphics().drawImage(image, 0, 0, null);
            }else{
                int x = 0;
                int y = 0;

                for (int nodeIndex = 0; nodeIndex < children.getLength(); nodeIndex++){
                    Node nodeItem = children.item(nodeIndex);

                    if (nodeItem.getNodeName().equals("ImageDescriptor")){
                        NamedNodeMap map = nodeItem.getAttributes();

                        x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
                        y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
                    }
                }

                if (disposal.equals("restoreToPrevious")){
                    BufferedImage from = null;
                    for (int i = frameIndex - 1; i >= 0; i--){
                        if (!frames.get(i).getDisposal().equals("restoreToPrevious") || frameIndex == 0){
                            from = frames.get(i).getImage();
                            break;
                        }
                    }

                    {
                        ColorModel model = from.getColorModel();
                        boolean alpha = from.isAlphaPremultiplied();
                        WritableRaster raster = from.copyData(null);
                        master = new BufferedImage(model, raster, alpha, null);
                    }
                }else if (disposal.equals("restoreToBackgroundColor") && backgroundColor != null){
                    if (!hasBackround || frameIndex > 1){
                        master.createGraphics().fillRect(lastx, lasty, frames.get(frameIndex - 1).getWidth(), frames.get(frameIndex - 1).getHeight());
                    }
                }
                master.createGraphics().drawImage(image, x, y, null);

                lastx = x;
                lasty = y;
            }

            {
                BufferedImage copy;

                {
                    ColorModel model = master.getColorModel();
                    boolean alpha = master.isAlphaPremultiplied();
                    WritableRaster raster = master.copyData(null);
                    copy = new BufferedImage(model, raster, alpha, null);
                }
                frames.add(new ImageFrame(copy, delay, disposal, image.getWidth(), image.getHeight()));
            }

            master.flush();
        }
        reader.dispose();

        return frames.toArray(new ImageFrame[frames.size()]);
    }

    public void remake(){
        try {
            FileInputStream fiStream = new FileInputStream( gifFile );

            ImageFrame[] frames = readGif( fiStream );
            ImageOutputStream output =
                    new FileImageOutputStream( new File( "NewGif.gif" ) );

            GifSequenceWriter writer =
                    new GifSequenceWriter( output, frames[0].getImage().getType(), frames[0].getDelay(), true );

            for( int i = 0; i < frames.length; i++ ){
                //code to resize the image
                System.out.println(i);
                BufferedImage image = new BufferedImage(width, heigth, frames[i].getImage().getType());
                Graphics2D g2d = image.createGraphics();
                g2d.drawImage(frames[i].getImage(), 0, 0, width, heigth, null);
                g2d.dispose();
                writer.writeToSequence( image );
            }

            writer.close();
            output.close();
        }
        catch ( FileNotFoundException e ) {
            System.out.println( "File not found" );
        }
        catch ( IOException e ) {
            System.out.println( "IO Exception" );
        }
    }

    public static void main(String[] args) {
        GifResizer resizer = new GifResizer("Resources/a5npVry_460sv_1.gif",1500,1000);
        System.out.println("start");
        resizer.remake();
        System.out.println("new gif made");
    }

}
