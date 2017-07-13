package mainFrame;

import com.apple.eawt.Application;
import com.apple.eawt.FullScreenUtilities;
import editoR.Editor;
import map.Map;
import editoR.unoRedo.Arrangement;
import statusPanel.BottomPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bobvv on 5/28/17.
 */
public class MainFrame extends JFrame
{

    public static void main(String[] args)
    {
        MainFrame mf = new MainFrame();
    }

    public MainFrame()
    {
        setFullScreen(this);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Map map = new Map(100,200);

        Canvas canvas = new Canvas(160,2,map,this);
        canvas.setLocation(0,0);
        canvas.setSize(this.getWidth(),this.getHeight() - this.getHeight()/3);
        addMouseListener(canvas);
        addMouseWheelListener(canvas);
        addKeyListener(canvas);
        addMouseMotionListener(canvas);
        add(canvas);

        Arrangement arrangement = new Arrangement();
        arrangement.setMap(map);

        Editor editor = new Editor(map, arrangement);
        canvas.addMousePressListener(editor);
        canvas.addMouseDragListener(editor);
        editor.setCanvasInterface(canvas);
        editor.setCanvasPainter(canvas);

        arrangement.setEditor(editor);

        BottomPanel bottomPanel = new BottomPanel(this.getWidth(),this.getHeight()/3, map, canvas, editor, arrangement, this);
        bottomPanel.setLocation(0,this.getHeight() - this.getHeight()/3);
        add(bottomPanel);

        canvas.setBottomPanel(bottomPanel);
        editor.setBottomPanel(bottomPanel);

        this.setVisible(false);
        this.setVisible(true);
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
