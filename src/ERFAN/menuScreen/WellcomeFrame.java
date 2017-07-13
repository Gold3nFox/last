package ERFAN.menuScreen;

import ERFAN.menuScreen.SongPlayer.SongPlayer;
import ERFAN.networking.Server;
import Utils.ImageReader;
import com.apple.eawt.Application;
import com.apple.eawt.FullScreenUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.util.Vector;

//import ERFAN.networking.Client;

public class WellcomeFrame extends JFrame{

    public static void main(String[] args) {
        WellcomeFrame screen = new WellcomeFrame();
        screen.repaint();
    }

    private int buttonWidth, buttonHeight;
    private int firstPint;
    private Font buttonFont = new Font("Arial", Font.BOLD, 30);
    private ImageIcon backGroundImage = ImageReader.createImage("/Assets/ErfanImages/thumb-1920-713530.jpg");
    private ImageIcon buttonLeftIcon = ImageReader.createImage("/Assets/ErfanImages/Warcraft-Sword-PNG.png");
    private ImageIcon buttonRightIcon = ImageReader.createImage("/Assets/ErfanImages/Warcraft-Sword-PNG1.png");
    private ImageIcon multyplayerScreen = ImageReader.createImage(("/Assets/ErfanImages/photo_2017-07-11_14-23-47.jpg"));
    private Vector<ButtonLikePanel> butttons;
    private Timer fps = new Timer(10, e -> {
        this.repaint();
    });
    private boolean paintMainScreen = true;

    private Server theServer;
    private InetAddress serverAddress;
    private int port;
    private String loginMassage;
//    private Client massengerClient;

    public WellcomeFrame(){
        setFullScreen(this);
//        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.black);

        buttonWidth = getWidth()/4;
        buttonHeight = getHeight()/7;

        // buttons
        butttons = new Vector<>();
        butttons.add(new ButtonLikePanel("new game",buttonWidth, buttonHeight,getWidth()/8,buttonHeight,buttonLeftIcon,this));
        butttons.add(new ButtonLikePanel("join a server",buttonWidth,buttonHeight,getWidth()/8,buttonHeight*2,buttonRightIcon,this));
        butttons.add(new ButtonLikePanel("load saved game",buttonWidth,buttonHeight,getWidth()/8,buttonHeight*3,buttonLeftIcon,this));
        butttons.add(new ButtonLikePanel("Editor",buttonWidth,buttonHeight,getWidth()/8,buttonHeight*4,buttonRightIcon,this));
        butttons.add(new ButtonLikePanel("Creat New Server",buttonWidth,buttonHeight,getWidth()/8,buttonHeight*5,buttonLeftIcon,this));
        for(ButtonLikePanel button : butttons){
            this.add(button);
        }

        SongPlayer songPlayer = new SongPlayer();
        songPlayer.play("/Users/bobvv/Downloads/esm/src/Assets/ErfanImages/Star_vs_the_Forces_of_Evil_-_Theme_Song_360_mp3_pm.wav");
        this.add(new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                //super.paintComponent(g);
                if(paintMainScreen){
                    g.drawImage(backGroundImage.getImage(),0,0,getWidth(),getHeight(),null);
                    g.setColor(Color.white);
                    g.setFont(buttonFont);
                    g.drawString("New Game",getWidth()/8,buttonHeight);
                    g.drawString("Join A Server",getWidth()/8,buttonHeight*2);
                    g.drawString("Load Saved Game",getWidth()/8,buttonHeight*3);
                    g.drawString("Editor",getWidth()/8,buttonHeight*4);
                    g.drawString("Create New Server",getWidth()/8,buttonHeight*5);

                }
                else{
                    disableButtons();
                    g.setColor(Color.white);
                    g.setFont(buttonFont);
                    g.drawImage(multyplayerScreen.getImage(),0,0,getWidth(),getHeight(),null);
                    g.drawString("Server IP:"+serverAddress,getWidth()/8,buttonHeight);
                    g.drawString("Server Port:"+port, getWidth()/8,buttonHeight/2*3);
                    g.drawString("Login Massage: "+loginMassage,getWidth()/8,buttonHeight/2*4);
                }

            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("got a key");
                if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
                    setPaintMainScreen(true);
                    enableButtons();
                }
            }
        });
        fps.start();
        setVisible(true);
    }
    private void disableButtons(){
        for(ButtonLikePanel button : butttons){
            button.setVisible(false);
            button.setLocation(5000,5000);
        }
    }
    private void enableButtons(){
        int a = buttonHeight;
        for(ButtonLikePanel button :butttons){
            button.setVisible(true);
            button.setLocation(getWidth()/8,a);
            a+=buttonHeight;
        }
    }


//    private void setFullScreen(JFrame frame) {
//        frame.setResizable(false);
//        frame.setUndecorated(true);
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice gd = ge.getDefaultScreenDevice();
//        gd.setFullScreenWindow(frame);
//    }
    private void setFullScreen(JFrame myFrame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setSize(new Dimension(width,height));
        FullScreenUtilities.setWindowCanFullScreen(myFrame,true);
        Application.getApplication().requestToggleFullScreen(myFrame);
    }

    // gettes and setters :


    public void setLoginMassage(String loginMassage) {
        this.loginMassage = loginMassage;
    }

    public Server getTheServer() {
        return theServer;
    }

    public void setTheServer(Server theServer) {
        this.theServer = theServer;
    }

    public void setServerAddress(InetAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPaintMainScreen(boolean paintMainScreen) {
        this.paintMainScreen = paintMainScreen;
    }

    public String getLoginMassage() {
        return loginMassage;
    }

    public boolean isPaintMainScreen() {
        return paintMainScreen;
    }

//    public void setMassengerClient(Client massengerClient) {
//        this.massengerClient = massengerClient;
//    }
}
