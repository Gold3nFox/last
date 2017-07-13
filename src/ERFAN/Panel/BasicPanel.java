package ERFAN.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BasicPanel extends JPanel{

    private int width, heigth, xRoot, yRoot;
    private String name;
    private int hitpoints, maxHP;
    protected static HashMap<String, String> iconTable;

    private JProgressBar healthBar;
    private JLabel nameLable, imageLable, hp = new JLabel("HP");
    private JButton killButton;
    static {
        iconTable = new HashMap<>();
        iconTable.put("bulding", "/Assets/buildings/building.jpg");
    }

    public BasicPanel(int width, int heigth, int xRoot, int yRoot, int hitpoints, int maxHP, String name){
        this.width = width;
        this.heigth = heigth;
        this.xRoot = xRoot;
        this.yRoot = yRoot;
        this.hitpoints = hitpoints;
        this.maxHP = maxHP;
        this.name = name;

        this.setSize(width, heigth);
        this.setLocation(xRoot, yRoot);
        this.setLayout(null);
        this.setBackground(Color.green);

        healthBar = new JProgressBar(0,maxHP);
        healthBar.setBackground(Color.red);
        healthBar.setForeground(Color.green);
        healthBar.setValue(hitpoints); // sets the current value to the dudes current hp
        healthBar.setSize(width/2, heigth/6);
        healthBar.setLocation(width/3, heigth/6);
        this.add(healthBar);

        nameLable = new JLabel(name);
        nameLable.setSize(width/4, heigth/6);
        nameLable.setLocation(0,0);
        this.add(nameLable);

        imageLable = new JLabel("bulding");
//        System.out.println(iconTable.get(name));
        imageLable.setSize(width/4,heigth/6*5);
        imageLable.setLocation(0,heigth/6);

        this.add(imageLable);

        hp.setSize(width/12,heigth/6);
        hp.setLocation(width/4,heigth/6);
        this.add(hp);

        killButton = new JButton("X");
        killButton.setForeground(Color.red);
        killButton.setSize(50,50);
        killButton.setLocation(width-55,0);
        this.add(killButton);
    }

    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setLayout(null);
//        frame.setSize(1000,1000);
////        BasicPanel panel = new BasicPanel(980,300,0,700,50,100,"bulding");
////        portPanel mp = new portPanel(350,100,0,200,50,100,"lol");
//        frame.add(mp);
////        frame.add(panel);
//        frame.setVisible(true);
    }

    // getters and setters :


    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getxRoot() {
        return xRoot;
    }

    public void setxRoot(int xRoot) {
        this.xRoot = xRoot;
    }

    public int getyRoot() {
        return yRoot;
    }

    public void setyRoot(int yRoot) {
        this.yRoot = yRoot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
