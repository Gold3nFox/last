package mainFrame;


import com.apple.eawt.Application;
import com.apple.eawt.FullScreenUtilities;
import editoR.Editor;
import editoR.unoRedo.Arrangement;
import map.Map;
import mobile.Mobile;
import mobile.MobileType;
import statusPanel.BottomPanelTest;

import javax.swing.*;
import java.awt.*;


/**
 * Created by bobvv on 5/28/17.
 */
public class GameFrame extends JFrame
{
    private BottomPanelTest bp;

    public static void main(String[] args)
    {
        GameFrame mf = new GameFrame();
    }

    public GameFrame()
    {
        setFullScreen(this);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Map map = new Map(100,200);

        Canvas canvas = new Canvas(160,2,map,this);
        canvas.setLocation(0,0);
        canvas.setSize(this.getWidth(),this.getHeight() - this.getHeight()/3);

        this.addMouseListener(canvas);
        this.add(canvas);
//        this.addMouseWheelListener(canvas);
        this.addKeyListener(canvas);
        this.addMouseMotionListener(canvas);

        Arrangement arrangement = new Arrangement();
//        arrangement.setCanvasPainter(canvas);

        Editor editor = new Editor(map, arrangement);
        canvas.addMousePressListener(editor);
        canvas.addMouseDragListener(editor);
        editor.setCanvasInterface(canvas);
        editor.setCanvasPainter(canvas);

        arrangement.setEditor(editor);
        arrangement.setMap(map);

        bp = new BottomPanelTest(this.getWidth(),this.getHeight()/3, map, canvas, editor, arrangement, this);
        bp.setLocation(0,this.getHeight() - this.getHeight()/3);
        add(bp);

        editor.setBottomPanelTest(bp);
        canvas.setBottomPanel(bp);
        canvas.setEditor(editor);

        map.setCanvas(canvas);

        Mobile mobile = new Mobile(200,200, bp, bp.getTabbedPane(), editor, map, MobileType.Normal);
        map.addMobile(mobile);


        bp.removeAll();
        bp.setMiniMap();
        bp.setStoragePanel();
        bp.tabbedPaneReset();
//        this.addKeyListener(mobile);

//        editor.setTabbedPane(bp.getTabbedPane());

        this.setVisible(false);
        this.setVisible(true);
    }

    public void loadsavedgame(){
        bp.load();

    }
    private void setFullScreen(JFrame myFrame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.setSize(new Dimension(width,height));
        FullScreenUtilities.setWindowCanFullScreen(myFrame,true);
        Application.getApplication().requestToggleFullScreen(myFrame);
    }

}
