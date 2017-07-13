package statusPanel.miniMap;

import mainFrame.Canvas;
import mainFrame.GameFrame;
import mainFrame.MainFrame;
import map.Map;

import javax.swing.*;
import java.awt.*;

public class MiniMap extends JPanel
{
    public Map map;
    private Canvas canvas;
    private MainFrame mainFrame;
    private GameFrame gameFrame;

    private int tileSize;
    private int width,height;

    public MiniMap(Map map, Canvas canvas, MainFrame mainFrame)
    {
        this.map = map;
        this.canvas = canvas;
        this.mainFrame = mainFrame;

        tileSize = 4;
        this.height = (map.getHeightCoord() * tileSize)/4;
        this.width = map.getWidthCoord() * tileSize;

        this.setSize(width,height);
        this.setBorder(BorderFactory.createEtchedBorder(Color.black,Color.BLUE));

        setBackground(Color.DARK_GRAY);
    }

    public MiniMap(Map map, Canvas canvas, GameFrame gameFrame)
    {
        this.map = map;
        this.canvas = canvas;
        this.gameFrame = gameFrame;

        tileSize = 4;
        this.height = (map.getHeightCoord() * tileSize)/4;
        this.width = map.getWidthCoord() * tileSize;

        this.setSize(width,height);
        this.setBorder(BorderFactory.createEtchedBorder(Color.black,Color.BLUE));

        setBackground(Color.DARK_GRAY);
    }

    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g2 = (Graphics2D) g0;
        for (int i = 0 ; i < map.getWidthCoord() ; i++)
            for (int j = 0 ; j < map.getHeightCoord() ; j++)
                map.getTile(i,j).simpleDraw(g2,1,1,tileSize,2,false);
        if (mainFrame != null) {
            int sizeWidth = (tileSize * mainFrame.getWidth()) / canvas.getTilesize();
            int sizeHeight = (tileSize * mainFrame.getHeight() * 2) / (3 * canvas.getTilesize());
            float thickness = 1;
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(thickness));
            g2.setColor(Color.BLACK);
            g2.drawRect((canvas.getxRoot() - 1) * tileSize, (canvas.getyRoot() - 1) * tileSize / 4, sizeWidth, sizeHeight);
            g2.setStroke(oldStroke);
        }else {
            int sizeWidth = (tileSize * gameFrame.getWidth()) / canvas.getTilesize();
            int sizeHeight = (tileSize * gameFrame.getHeight() * 2) / (3 * canvas.getTilesize());
            float thickness = 1;
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(thickness));
            g2.setColor(Color.BLACK);
            g2.drawRect((canvas.getxRoot() - 1) * tileSize, (canvas.getyRoot() - 1) * tileSize / 4, sizeWidth, sizeHeight);
            g2.setStroke(oldStroke);
        }

    }

}
