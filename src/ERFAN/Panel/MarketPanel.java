package ERFAN.Panel;

import javax.swing.*;
import java.awt.*;

public class MarketPanel extends BasicPanel {

    private JButton convert;
    private int width,height;
    private static String[] lableFunctions = {"gold","lumber","convert_arrows"};
    public MarketPanel(int width, int heigth, int xRoot, int yRoot, int hitpoints, int maxHP, String name) {
        super(width, heigth, xRoot, yRoot, hitpoints, maxHP, name);

        this.width = width;
        this.height = heigth;
        this.setBackground(Color.blue);
        int lableWindth = getWidth()/6;
        int lableHeight = getHeight()/6;
        int begining = 10;
        for(int i=0;i<3;i++){
            JLabel temp = new JLabel(lableFunctions[i]);
            temp.setSize(lableWindth,lableHeight);
            temp.setLocation((begining+i)*getWidth()/6,heigth/2);
            this.add(temp);
        }
        convert = new JButton("Convert");
        convert.setSize(lableWindth, lableHeight);
        convert.setLocation(width/6*5, heigth/2);
        this.add(convert);
    }
//    public void paint(Graphics g) {
//        super.paint(g);
//        System.out.println(getX() + " " + getY() + " " + getWidth() + " " + getHeight());
////        g.drawImage(ImageReader.createImage("/Assets/panel.png").getImage(),0,0,width/2,height,null);
//    }
}
