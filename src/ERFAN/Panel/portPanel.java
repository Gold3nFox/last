package ERFAN.Panel;

import editoR.Editor;
import mainFrame.CanvasInterface;
import map.Map;
import mobile.Mobile;
import mobile.MobileType;
import statusPanel.BottomPanelTest;
import statusPanel.tabbedPane.SelectionTabbedPane;

import javax.swing.*;
import java.awt.*;

public class portPanel extends BasicPanel {

    private JButton convert;
    private static String lableFunctions = "ship";

    private int x;
    private int y;


    public portPanel(int width, int heigth, int xRoot, int yRoot, int hitpoints, int maxHP, String name, Map map, int x, int y,CanvasInterface canvasInterface, BottomPanelTest bottomPanel,SelectionTabbedPane selectionTabbedPane,Editor editor){
        super(width, heigth, xRoot, yRoot, hitpoints, maxHP, name);

        this.setBackground(Color.blue);
        int lableWindth = getWidth()/6;
        int lableHeight = getHeight()/6;
        int begining = 10;
        JLabel temp = new JLabel(lableFunctions);
        temp.setSize(lableWindth,lableHeight);
        temp.setLocation((begining)*getWidth()/6,heigth/2);
        this.add(temp);
        convert = new JButton("Convert");
        convert.setSize(lableWindth, lableHeight);
        convert.setLocation(width/6*5, heigth/2);
        this.add(convert);

        convert.addActionListener(e->
        {
            if(map.getGold() >= 50) {
                map.addMobile(new Mobile(x * canvasInterface.getTilesize() - 50, y * canvasInterface.getTilesize() / (canvasInterface.getCotang() * 2) + 10, bottomPanel, selectionTabbedPane, editor, map, MobileType.Normal));
                //bayad ship beshe
                map.setGold(map.getGold() - 50);
                System.out.println(map.getGold() + " -----");
            }
        });

    }

//    public void paint(Graphics g) {
//        super.paint(g);
//        System.out.println(getX() + " " + getY() + " " + getWidth() + " " + getHeight());
////        g.drawImage(ImageReader.createImage("/Assets/panel.png").getImage(),0,0,width/2,height,null);
//    }
}
