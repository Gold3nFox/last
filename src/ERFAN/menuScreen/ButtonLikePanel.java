package ERFAN.menuScreen;

import ERFAN.networking.Server;
import mainFrame.GameFrame;
import mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonLikePanel extends JPanel{

    private ImageIcon buttonImage;
    private int width,heigth,x,y;
    private int currentWidth, currentHeight;
    private int currentX, currentY;
    private String function;
    private WellcomeFrame parent;
    public ButtonLikePanel(String name,int width, int heigth, int x, int y,ImageIcon icon,WellcomeFrame parrent) {
        this.parent = parrent;
        this.function = name;
        this.width = width;
        this.heigth = heigth;
        this.x = x;
        this.y = y;
        this.currentX=x;
        this.currentY=y;
        this.buttonImage = icon;
        this.currentHeight = heigth;
        this.currentWidth = width;
        this.setSize(width,heigth);
        this.setLocation(x,y);
        this.setOpaque(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(name+" got pressed");
                animate();
            }
        });
    }

    private void doCommand(){
        // this part really should be handled via reflection ...
        switch (function.toLowerCase()){
            case "new game":
                GameFrame gf = new GameFrame();
                break;
            case "load saved game":
                GameFrame gmf = new GameFrame();
                gmf.loadsavedgame();
                break;
            case "creat new server":
                Server server = new Server();
                parent.setLoginMassage(server.getLoginMassage());
                parent.setServerAddress(server.getServerIP());
                parent.setPort(server.getServerPortNumber());
                parent.setTheServer(server);
                parent.setPaintMainScreen(false);
                parent.repaint();
                break;
            case "editoR":
                MainFrame mf = new MainFrame();
                break;
        }
    }
    private void animate(){
        new Thread(() -> {
            for(int i=1;i<20;i++){
//                    currentWidth+=i*100;
//                    ButtonLikePanel.this.setSize(currentWidth,currentHeight);
                ButtonLikePanel.this.repaint();
                currentX-=10;
                ButtonLikePanel.this.setLocation(currentX, currentY);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            doCommand();
            currentX =x;
            currentY=y;
            ButtonLikePanel.this.setLocation(currentX, currentY);
//                currentHeight = heigth;
//                currentWidth = width;
//                ButtonLikePanel.this.setSize(width,heigth);

        }).start();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buttonImage.getImage(),0,0,currentWidth,currentHeight,null);
    }
}
