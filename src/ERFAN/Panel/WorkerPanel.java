package ERFAN.Panel;

import Utils.ImageReader;
import editoR.Editor;
import mainFrame.CanvasInterface;
import map.Map;
import mobile.Mobile;
import mobile.MobileType;
import statusPanel.BottomPanelTest;
import statusPanel.tabbedPane.SelectionTabbedPane;

import javax.swing.*;
import java.awt.*;

public class WorkerPanel extends BasicPanel
{

    private int numberOfButtons = 2;
    private static String[] buttonFunctions = {"Builder","Soldier"};
    private JButton[] buttons;

    private int x;
    private int y;
    private CanvasInterface canvasInterface;

    private BottomPanelTest bottomPanel;
    private SelectionTabbedPane selectionTabbedPane;
    private Editor editor;

    private Map map;


    public WorkerPanel(int width, int heigth, int xRoot, int yRoot, int hitpoints, int maxHP, String name, Map map, CanvasInterface canvasInterface, int x, int y, BottomPanelTest bottomPanel, SelectionTabbedPane selectionTabbedPane, Editor editor)
    {
        super(width, heigth, xRoot, yRoot, hitpoints, maxHP, name);
        this.map = map;
        this.bottomPanel = bottomPanel;
        this.selectionTabbedPane = selectionTabbedPane;
        this.editor = editor;
        buttons = new JButton[3];
        makeButtons();

        this.x = x;
        this.y = y;
        this.canvasInterface = canvasInterface;
    }


    int  root =0;
    private void makeButtons()
    {
        int buttonWidth = getWidth()/6;
        int buttonHeight = getHeight()/6;
        int begin = 70;

        for(int i=0;i<numberOfButtons;i++)
        {
            buttons[i] = new JButton(buttonFunctions[i]);
            buttons[i].setSize(buttonWidth, buttonHeight);
            buttons[i].setLocation(begin + i*getWidth()/6,getHeigth()/2);
            this.add(buttons[i]);
        }
        buttons[0].addActionListener(e->
        {
            if(map.getGold() >= 50) {
                map.addMobile(new Mobile(x * canvasInterface.getTilesize() - 50, y * canvasInterface.getTilesize() / (canvasInterface.getCotang() * 2) + 10 + root, bottomPanel, selectionTabbedPane, editor, map, MobileType.Normal));
                root += 50;
                map.setGold(map.getGold() - 50);
                System.out.println(map.getGold() + " -----");
            }
        });
        buttons[1].addActionListener(e->
        {
            if (map.getGold() >= 100) {
                map.addMobile(new Mobile(x * canvasInterface.getTilesize() - 50, y * canvasInterface.getTilesize() / (canvasInterface.getCotang() * 2) + 10 + root, bottomPanel, selectionTabbedPane, editor, map, MobileType.SOLDIER));
                root += 50;
                map.setGold(map.getGold() - 100);
                System.out.println(map.getGold() + " -----");
            }
        });
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(ImageReader.createImage("/Assets/panel.png").getImage(),getX(),getY(),getWidth(),getHeight(),null);
    }
}
