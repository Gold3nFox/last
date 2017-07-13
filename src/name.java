import Utils.ImageReader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sarb on 7/8/17.
 */
public class name extends JPanel
{
    public name()
    {
        setSize(400,400);
        setBackground(Color.cyan);

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        Image image = ImageReader.createImage("/Assets/resources/shallow_fish/shallow_fish1.gif").getImage();

        g2.drawImage(image,200,200,this);

    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("test");
        frame.setLayout(null);
        frame.setSize(500,500);
        frame.add(new name());
        frame.setVisible(true);
    }
}
