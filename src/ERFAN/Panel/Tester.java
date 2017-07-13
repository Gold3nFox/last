package ERFAN.Panel;

import javax.swing.*;

/**
 * Created by Asus on 7/11/2017.
 */
public class Tester {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(1000,1000);
        BasicPanel panel = new MarketPanel(980,300,0,700,50,100,"bulding");
        frame.add(panel);
        frame.setVisible(true);
    }

}
