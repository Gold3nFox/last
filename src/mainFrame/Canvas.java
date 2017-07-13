package mainFrame;


import editoR.Editor;
import mobile.Mobile;
import map.Map;
import statusPanel.BottomPanel;
import statusPanel.BottomPanelTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import static java.lang.StrictMath.min;

public class Canvas extends JPanel implements MouseWheelListener, KeyListener, MouseMotionListener, MouseListener, CanvasPainter, CanvasInterface
{


    private static int TILE_MIN_SIZE = 20 ,TILE_MAX_SIZE = 300;
    private int tileSize;
    private int xRoot , yRoot;
    private int cotang;

    private Timer paintTimer;
    private Timer updateHuman;

    private Vector<MousePressListener> mousePressListeners;
    private Vector<MouseDragListener> mouseDragListeners;
    private Vector<MyMouseListener> myMouseListeners;
    private Map map;
    private MainFrame mainFrame;
    private GameFrame gameFrame;

    int widthCoord;
    int heightCoord;

    private Rectangle draggedRect;

    private Editor editor;

    private BottomPanel bottomPanel;
    private BottomPanelTest bottomPanelTest;

    private boolean isSimple = false;
    private boolean isCoordinate = true;

    private MouseAbilities mouseAbilities;

    private Direction previousDirection;

    public Canvas(int tileSize, int cotang, Map map, MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        this.cotang = cotang;
        this.tileSize = tileSize;
        this.widthCoord = map.getWidthCoord();
        this.heightCoord = map.getHeightCoord();
        this.map = map;

        this.mouseDragListeners = new Vector<>();
        this.mousePressListeners = new Vector<>();

        TILE_MIN_SIZE = min( 2560/widthCoord, (1600*cotang)/heightCoord) ;
        xRoot = 1;
        yRoot = 1;

    }

    public void setTarget(MouseEvent e)
    {
        for (Mobile mobile: map.getMobiles())
            if (mobile.isSelected())
                mobile.setTarget(e,xRoot,yRoot,tileSize);
    }

    public Canvas(int tileSize, int cotang, Map map, GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
        this.cotang = cotang;
        this.tileSize = tileSize;
        this.widthCoord = map.getWidthCoord();
        this.heightCoord = map.getHeightCoord();
        this.map = map;

        this.mouseDragListeners = new Vector<>();
        this.mousePressListeners = new Vector<>();
        this.myMouseListeners = new Vector<>();

        TILE_MIN_SIZE = min( 2560/widthCoord, (1600*cotang)/heightCoord) ;
        xRoot = 1;
        yRoot = 1;

//
        updateHuman = new Timer(100, e-> this.repaint());
        updateHuman.start();

        mouseAbilities = new MouseAbilities(this);
//        update = new Timer(100, e-> this.repaint());
//        update.start();
    }



    public void addMousePressListener(MousePressListener mousePressListener)
    {
        this.mousePressListeners.add(mousePressListener);
    }

    public void addMouseDragListener(MouseDragListener mouseDragListener)
    {
        this.mouseDragListeners.add(mouseDragListener);
    }

    public void checkRect()
    {
        for (Mobile mobile:map.getMobiles())
            mobile.setSelected(false);
        if (draggedRect!=null)
            for(Mobile mobile:map.getMobiles())
                if (draggedRect.contains(mobile.getPoint(xRoot,yRoot,tileSize)))
                    mobile.setSelected(true);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (e.getModifiers() == 1)
            return;
        int speed = (int) e.getPreciseWheelRotation() * 4;
        int newSize = tileSize - speed;

        if( newSize <= TILE_MIN_SIZE )
        {
            tileSize = TILE_MIN_SIZE;
            repaint();
        }
        else if( newSize >= TILE_MAX_SIZE)
        {
            tileSize = TILE_MAX_SIZE;
            repaint();
        }
        else
        {
            tileSize = newSize;
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if ( yRoot > 1 )
                {
                    yRoot = yRoot -1;
                    repaint();
                }
                break;
            case KeyEvent.VK_DOWN:
                if( yRoot < getHeightCoord() - getVerticalTiles())
                {
                    yRoot = yRoot + 1;
                    repaint();
                }
                break;
            case KeyEvent.VK_LEFT:
                if ( xRoot > 1 )
                {
                    xRoot = xRoot - 1;
                    repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if ( xRoot < getWidthCoord() - getHorizontalTiles())
                {
                    xRoot = xRoot + 1;
                    repaint();
                }
                if(mainFrame == null)
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseAbilities.mouseDragged(e);
        for(MouseDragListener mouseDragListener: mouseDragListeners)
            mouseDragListener.mouseDragged(e);

    }

    private int[][][] neighbors8 = { {{-1, -1}, {-1, 1}, {0, -1}, {0, -2}, {1, 0}, {0, 2}, {0, 1}, {-1, 0}} , {{1, -1}, {1, 1}, {0, -1}, {0, -2}, {1, 0}, {0, 2}, {0, 1}, {-1,0}} };

    private boolean checkInBoard(int x, int y)
    {
        if (x >= 0 && x < map.getWidthCoord() && y >= 0 && y < map.getHeightCoord())
            return true;
        return false;
    }

    Mobile selected = null;

    @Override
    public void mousePressed(MouseEvent e)
    {
//        if (selected != null)
//        {
//            System.out.println("here");
//            build(selected, e);
        mouseAbilities.mousePressed(e);
        for(MousePressListener mousePressListener : mousePressListeners)
            mousePressListener.mousePressed(e);
        editor.place(e);
//            return;
//        }
    }

    public void checkMobiles(MouseEvent e)
    {
        for (Mobile mobile:map.getMobiles())
            if (mobile.getPolygon(xRoot,yRoot,tileSize,2).contains(e.getPoint()))
            {
                selected = mobile;
                mobile.showPanel();
                break;
            }
    }

    private void build(Mobile mobile,MouseEvent e)
    {
        if (bottomPanelTest.getTabbedPane().getBrushType() == Editor.BrushType.BUILDING )
        {
            System.out.println("there");
            int xtile = xRoot + e.getX() / tileSize;
            int ytile = yRoot + (e.getY() * 2 * cotang) / tileSize;

            int bin = ytile % 2;
            boolean isValid = false;
            for (int i = 0; i < 8; i++)
            {
                int xx = xtile + neighbors8[bin][i][0];
                int yy = ytile + neighbors8[bin][i][1];
                if (checkInBoard(xx, yy) && map.getTile(xx, yy).getPolygon(xRoot, yRoot, tileSize, cotang).contains(e.getPoint()))
                {
                    xtile = xx;
                    ytile = yy;
                    isValid = true;
                }
            }
            if (!isValid)
            {
                int xx = xtile;
                int yy = ytile;
                if (checkInBoard(xx, yy) && map.getTile(xx, yy).getPolygon(xRoot, yRoot, tileSize, cotang).contains(e.getPoint()))
                    isValid = true;
            }

            System.out.println(xtile + " " + ytile);

            if (!isValid)
                return;

            boolean test = bottomPanelTest.getTabbedPane().getBuilding().getAvailableTerrain() == map.getTile(xtile, ytile).getTerrainType();
            System.out.println(test);
            System.out.println(bottomPanelTest.getTabbedPane().getBuilding().getAvailableTerrain().toString() + " " + map.getTile(xtile,ytile).getTerrainType() );
            if (map.getTile(xtile, ytile).getFiller() == null && bottomPanelTest.getTabbedPane().getBuilding().getAvailableTerrain() == map.getTile(xtile, ytile).getTerrainType())
            {
                System.out.println(3333);
                editor.placeBuilding(bottomPanelTest.getTabbedPane().getBuilding(), xtile, ytile);
            }
            bottomPanelTest.getTabbedPane().setBrushType(null);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if (gameFrame != null)
            gameFrame.requestFocus();
        else
            mainFrame.requestFocus();

        int xPoint = e.getX();
        int yPoint = e.getY();

        Direction dir = null;
        if ( xPoint == 0 && yPoint == 0)
            dir = Direction.UP_LEFT;
        else if( xPoint == 0 && yPoint > getHeight() * 3 / 2 - 10 )
            dir = Direction.DOWN_LEFT;
        else if(yPoint == 0 &&  xPoint > getWidth() -3)
            dir = Direction.UP_RIGHT;
        else if(yPoint > getHeight() * 3 / 2 - 10 && xPoint > getWidth() -3)
            dir = Direction.DOWN_RIGHT;
        else if ( xPoint == 0 )
            dir = Direction.LEFT;
        else if ( xPoint > getWidth() -3 )
            dir = Direction.RIGHT;
        else if ( yPoint == 0 )
            dir = Direction.UP;
        else if (yPoint > getHeight() * 3 / 2 - 10 )
            dir = Direction.DOWN;

        if (dir == previousDirection)
            return;

        if (paintTimer != null)
            paintTimer.stop();

        previousDirection = dir;
        Direction finalDir = dir;

        if (finalDir == null)
            return;

        paintTimer = new Timer(200,(ActionEvent event) ->
        {
            switch (finalDir)
            {
                case UP_RIGHT:
                    if (yRoot > 2 && xRoot < getWidthCoord() - getHorizontalTiles())
                    {
                        yRoot = yRoot - 1;
                        xRoot = xRoot + 1;
                        repaint();
                    }
                    break;
                case UP_LEFT:
                    if (yRoot > 2 && xRoot > 2)
                    {
                        yRoot = yRoot - 1;
                        xRoot = xRoot - 1;
                        repaint();
                    }
                    break;
                case DOWN_LEFT:
                    if (yRoot < getHeightCoord() - getVerticalTiles() && xRoot > 2)
                    {
                        yRoot = yRoot + 1;
                        xRoot = xRoot - 1;
                        repaint();
                    }
                    break;
                case DOWN_RIGHT:
                    if (yRoot < getHeightCoord() - getVerticalTiles() && xRoot < getWidthCoord() - getHorizontalTiles())
                    {
                        yRoot = yRoot + 1;
                        xRoot = xRoot + 1;
                        repaint();
                    }
                    break;
                case UP:
                    if (yRoot > 2)
                    {
                        yRoot = yRoot - 1;
                        repaint();
                    }
                    break;
                case DOWN:
                    if (yRoot < getHeightCoord() - getVerticalTiles())
                    {
                        yRoot = yRoot + 1;
                        repaint();
                    }
                    break;
                case LEFT:
                    if (xRoot > 2)
                    {
                        xRoot = xRoot - 1;
                        repaint();
                    }
                    break;
                case RIGHT:
                    if (xRoot < getWidthCoord() - getHorizontalTiles())
                    {
                        xRoot = xRoot + 1;
                        repaint();
                    }
                    break;
            }
        });

        paintTimer.start();
    }

    @Override
    public void repaintCanvas()
    {
        repaint();
    }

    @Override
    public void repaint(long tm, int x, int y, int width, int height)
    {
        super.repaint(tm, x, y, width, height);
        if (bottomPanel != null)
            bottomPanel.getMiniMap().repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
//        System.out.println(System.currentTimeMillis());
        Graphics2D g2 = (Graphics2D) g;
        isCoordinate =false;

        for (int j = 0; j < heightCoord; j++)
            for (int i = 0; i < widthCoord; i++)
                if (i > xRoot - 2 && j > yRoot - 2 && i < xRoot + getHorizontalTiles() + 1 && j < yRoot + getVerticalTiles() + 1)
                {
                    if (isSimple)
                        map.getTile(i, j).simpleDraw(g2, xRoot, yRoot, tileSize, cotang, isCoordinate);
                    else
                        map.getTile(i, j).draw(g2, xRoot, yRoot, tileSize, cotang, isCoordinate);
                }
        if (! (gameFrame == null) )
        {
            for (Mobile mobile : map.getMobiles()) {
                mobile.draw(g2, xRoot, yRoot, tileSize);
            }
        }
        if(draggedRect != null)
        {
            g2.setColor(Color.red);
            g2.draw(draggedRect);
        }
        g2.setColor(Color.magenta);
        g2.drawString("Gold :"+ map.getGold(),50,50);
    }

    public void addMyMouseListener(MyMouseListener myMouseListener)
    {
        myMouseListeners.add(myMouseListener);
    }

    public int getVerticalTiles()
    {
        return min((getHeight() * 2 * cotang) / tileSize, map.getHeightCoord()-1);
    }

    public int getHorizontalTiles()
    {
        return min(getWidth()/ tileSize, map.getWidthCoord()-1);
    }

    private enum Direction
    {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT;
    }

    @Override
    public int getTilesize()
    {
        return tileSize;
    }

    public int getWidthCoord()
    {
        return widthCoord;
    }

    public int getHeightCoord()
    {
        return heightCoord;
    }

    @Override
    public int getCotang()
    {
        return cotang;
    }

    @Override
    public int getXRoot()
    {
        return xRoot;
    }

    @Override
    public int getYRoot()
    {
        return yRoot;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        mouseAbilities.MouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    public Editor getEditor()
    {
        return editor;
    }

    public void setEditor(Editor editor)
    {
        this.editor = editor;
    }

    public void setDraggedRect(Rectangle draggedRect)
    {
        this.draggedRect = draggedRect;
    }

    public void setSimple(boolean simple)
    {
        isSimple = simple;
    }

    public void setCoordinate(boolean coordinate)
    {
        isCoordinate = coordinate;
    }

    public int getxRoot()
    {
        return xRoot;
    }

    public void setBottomPanel(BottomPanel bottomPanel)
    {
        this.bottomPanel = bottomPanel;
    }

    public void setBottomPanel(BottomPanelTest bottomPanelTest)
    {
        this.bottomPanelTest = bottomPanelTest;
    }

    public int getyRoot()
    {
        return yRoot;
    }
}
