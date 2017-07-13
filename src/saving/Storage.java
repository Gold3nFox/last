package saving;

import Utils.ImageReader;
import mainFrame.Canvas;
import mainFrame.GameFrame;
import mainFrame.MainFrame;
import map.Map;
import map.building.Building;
import map.resource.Resource;
import statusPanel.miniMap.MiniMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Created by sarb on 7/8/17.
 */
public class Storage extends JPanel
{    private Map map;
    private MainFrame mainframe;
    private GameFrame gameFrame;
    private MiniMap miniMap;
    private Canvas canvas;

    public Storage(Map map, MainFrame mainframe, Canvas canvas, MiniMap minimap)
    {
        this.map = map;
        this.mainframe = mainframe;
        this.canvas = canvas;
        this.miniMap = minimap;

        this.setLayout(null);
        this.setSize(100,100);

        JButton save = new JButton();
        save.setSize(50 , 50);
        save.setLocation(1,1);
        save.setIcon(ImageReader.createImage("/Assets/saveLoadIcons/save.jpg"));
        save.addActionListener(e ->
        {
            try
            {
                save();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        });
        add(save);

        JButton load = new JButton();
        load.setSize(50 , 50);
        load.setLocation(51,1);
        load.setIcon(ImageReader.createImage("/Assets/saveLoadIcons/load.jpg"));
        load.addActionListener(e ->
        {
            try
            {
                load();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
        });
        add(load);

    }

    public Storage(Map map, GameFrame gameFrame, Canvas canvas, MiniMap minimap)
    {
        this.map = map;
        this.gameFrame = gameFrame;
        this.canvas = canvas;
        this.miniMap = minimap;

        this.setLayout(null);
        this.setSize(100,100);

        JButton save = new JButton();
        save.setSize(50 , 50);
        save.setLocation(1,1);
        save.setIcon(ImageReader.createImage("/Assets/saveLoadIcons/save.jpg"));
        save.addActionListener(e ->
        {
            try
            {
                save();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        });
        add(save);

        JButton load = new JButton();
        load.setSize(50 , 50);
        load.setLocation(51,1);
        load.setIcon(ImageReader.createImage("/Assets/saveLoadIcons/load.jpg"));
        load.addActionListener(e ->
        {
            try
            {
                load();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
        });
        add(load);

    }


    public void save() throws IOException
    {
        MapEditorSave mapEditorSave = new MapEditorSave(map.getWidthCoord(),map.getHeightCoord());
        for (int i = 0 ; i < map.getWidthCoord() ; i ++)
            for (int j = 0 ; j < map.getHeightCoord() ; j ++)
            {
                mapEditorSave.setTerrainTypes(i,j,map.getTile(i,j).getTerrainType().getIndex());
                if (map.getTile(i,j).getFiller() instanceof Building)
                    mapEditorSave.setBuildingTypes(i,j,((Building) map.getTile(i,j).getFiller()).getBuildingType().getIndex());
                if (map.getTile(i,j).getFiller() instanceof Resource)
                    mapEditorSave.setResourceTypes(i,j,((Resource) map.getTile(i,j).getFiller()).getResourceType().getIndex());
            }

        for (int i = 0 ; i < map.getWidthCoord() ; i ++)
            for (int j = 0 ; j < map.getHeightCoord() ; j ++) {
                System.out.println(mapEditorSave.buildingTypes[i][j]);
                System.out.println(mapEditorSave.resourceTypes[i][j]);
                System.out.println(mapEditorSave.terrainTypes[i][j]);
            }


        final JFileChooser fileDialog = new JFileChooser(System.getProperty("user.dir"));
        fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileDialog.showSaveDialog(mainframe);

        String path=fileDialog.getSelectedFile().getAbsolutePath();
        File file = new File(path + ".map");


        FileOutputStream fos = null;
        fos = new FileOutputStream(file);

        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(fos);

        assert oos != null;
        oos.writeObject(map);
        oos.flush();
        oos.close();
    }

    public void load() throws IOException, ClassNotFoundException
    {
        final JFileChooser fileDialog = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("LOAD FILES", "map", "binary");
        fileDialog.setFileFilter(filter);
        int returnVal = fileDialog.showOpenDialog(mainframe);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            java.io.File file = fileDialog.getSelectedFile();
        }


        FileInputStream fis = null;
        fis = new FileInputStream(fileDialog.getSelectedFile());

        ObjectInputStream oin = null;
        oin = new ObjectInputStream(fis);

        Map map2 = null;

        assert oin != null;
        map2 = (Map) oin.readObject();

        map.replace(map2);
        miniMap.repaint();
        canvas.repaint();
    }
}
