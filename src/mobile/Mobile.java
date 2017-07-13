package mobile;

import Utils.ImageReader;
import editoR.Editor;
import map.Map;
import map.resource.Resource;
import statusPanel.BottomPanelTest;
import statusPanel.tabbedPane.SelectionTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

/**
 * Created by sarb on 7/8/17.
 */
public class Mobile implements KeyListener
{
    private int x;
    private int y;

    private boolean isSelected;

    protected Direction direction;
    protected int speed;
    protected double size;
    protected double height;

    private BottomPanelTest bottomPanelTest;
    private SelectionTabbedPane selectionTabbedPane;
    private Editor editor;

    private boolean wantResource = false;

    private Map map;
    private mainFrame.Canvas canvas;

    private MobileType mobileType;
    private Ability currentAbility;

    private Point target;

    private int index;

    public Mobile(int x, int y, BottomPanelTest bottomPanelTest, SelectionTabbedPane selectionTabbedPane, Editor editor, Map map, MobileType mobileType)
    {
        this.x = x;
        this.y = y;
        this.bottomPanelTest = bottomPanelTest;
        this.selectionTabbedPane = selectionTabbedPane;
        this.editor = editor;
        this.map = map;
        this.mobileType = mobileType;
        currentAbility = Ability.walk;
        direction = Direction.SOUTH;

        size = mobileType.getSize();
        height = mobileType.getHeight();
        speed = mobileType.getSpeed();

        canvas = map.getCanvas();
        target = new Point(x+1,y+1);

        index = 0;
    }

    public Point getPoint(int xroot, int yroot, int tileSize)
    {
        return new Point(x - xroot*tileSize, y - yroot*(tileSize/2));
    }

    public Polygon getPolygon(int xRoot, int yRoot, int size, int cotang)
    {
        Polygon polygon = new Polygon();

        int relX = x - xRoot*size + size/10;
        int relY = y - yRoot*(size/2) + (int)(this.size*size*height/2) + size/10;

        size = (int) (size*this.size);
        polygon.addPoint(relX, relY);
        polygon.addPoint( relX - size/2, relY + size/(2*cotang));
        polygon.addPoint(relX, relY + size/cotang);
        polygon.addPoint(relX + size/2, relY + size/(2*cotang));

        return polygon;
    }

    public void draw(Graphics g,int xroot, int yroot, int tileSize)
    {
        Graphics2D g2 = (Graphics2D) g;

        ImageIcon cur = ImageReader.createImage("/Assets/mobiles/" + mobileType.toString().toLowerCase() + "/" + currentAbility.toString().toLowerCase() + "/" + direction.getNum() + "/" + index + ".png");

        index = (index+1) % currentAbility.getFrame();
        move();
        g2.setColor(Color.blue);
        if(isSelected)
            g2.draw(getPolygon(xroot,yroot,tileSize,2));
        g2.setColor(Color.GREEN);
        g2.fillRect(x - xroot*tileSize - 5, y - yroot*(tileSize/2)-5, tileSize/3,5);
        g2.setColor(Color.red);
        if(target!=null)
            g2.fillRect((int)target.getX() - xroot*tileSize,(int)target.getY() - (yroot*tileSize)/2 + (int)(tileSize*size*height),5,5);
//        g2.setColor(Color.blue);
//        g2.fillRect(x - xroot*tileSize,y - yroot*(tileSize/2) + (int)(size*tileSize*height/2),10,10);
//        g2.setColor(Color.cyan);
//        g2.fill(getPolygon(xroot,yroot,tileSize,2));
        g2.drawImage(cur.getImage(),x - xroot*tileSize, y - yroot*(tileSize/2),(int)(size * tileSize), (int)(size*tileSize*height), null);
    }

    public void setTarget(MouseEvent mouseEvent ,int xroot, int yroot, int tileSize)
    {
        isWorking=false;
        wantResource =false;
        target = new Point(mouseEvent.getX() + xroot*tileSize,mouseEvent.getY() + (yroot*tileSize)/2 - (int)(tileSize*size*height));
        int x = xroot + mouseEvent.getX()/ tileSize;
        int y = yroot -1 + (mouseEvent.getY() * 4) / tileSize;
        System.out.println(x+ " " + y);
        if (map.getTile(x,y).getFiller() instanceof Resource)
        {
            this.target = new Point((int)(target.getX()-tileSize/5),(int)target.getY());
            wantResource = true;
        }
        else
            currentAbility = Ability.walk;
        index = 0;
    }

    private void updateDir()
    {
        Point point = new Point(x,y);
        if (point.getX() + speed < target.getX() && point.getY() - speed > target.getY() )
            this.direction = Direction.SOUTH_EAST;
        else if (point.getX() + speed < target.getX() && point.getY() + speed < target.getY() )
            this.direction = Direction.NORTH_EAST;
        else if (point.getX() - speed > target.getX() && point.getY() - speed > target.getY() )
            this.direction = direction.SOUTH_WEST;
        else if (point.getX() - speed > target.getX() && point.getY() + speed < target.getY() )
            this.direction = Direction.NORTH_WEST;
        else if( point.getX() + speed < target.getX() )
            this.direction = Direction.EAST;
        else if ( point.getX() - speed > target.getX())
            this.direction = Direction.WEST;
        else if (point.getY() - speed > target.getY())
            this.direction = Direction.SOUTH;
        else if (point.getY() + speed < target.getY())
            this.direction = Direction.NORTH;
        else
        {
            if (wantResource)
            {
                isWorking = true;
                this.currentAbility = Ability.fight;
                this.direction = Direction.EAST;
            }
            else
                this.direction = Direction.ZERO;
        }
    }

    private boolean isWorking = false;

    public void move()
    {
        updateDir();
        if (isWorking)
            return;
        else
            currentAbility = Ability.walk;
        x += speed * direction.getXspeed();
        y += speed * direction.getYspeed();
    }

    public void changeDirection(Direction direction)
    {

        this.direction = direction;
        index = 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    public void showPanel()
    {
        bottomPanelTest.setTabbedPane(true);
        bottomPanelTest.setVisible(false);
        bottomPanelTest.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                changeDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_DOWN:
                changeDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_LEFT:
                changeDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                changeDirection(Direction.EAST);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
}
