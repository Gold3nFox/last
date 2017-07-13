package statusPanel;

import editoR.Editor;
import editoR.unoRedo.Arrangement;
import mainFrame.Canvas;
import mainFrame.GameFrame;
import map.Map;
import saving.Storage;
import statusPanel.miniMap.MiniMap;
import statusPanel.options.Options;
import statusPanel.tabbedPane.SelectionTabbedPane;
import statusPanel.undoRedoPanel.UndoRedoPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by bobvv on 5/30/17.
 */
public class BottomPanelTest extends JPanel
{
    private Map map;
    private Canvas canvas;
    private Editor editor;
    private Arrangement arrangement;
    private GameFrame gameFrame;

    private MiniMap miniMap;
    private SelectionTabbedPane tabbedPane;
    private UndoRedoPanel undoRedoPanel;
    private Storage storage;
    private Options options;


    public BottomPanelTest(int width, int height, Map map, Canvas canvas, Editor editor, Arrangement arrangement, GameFrame gameFrame)
    {
        this.map = map;
        this.canvas = canvas;
        this.editor = editor;
        this.arrangement = arrangement;
        this.gameFrame = gameFrame;

        setSize(width, height);
        setBackground(Color.darkGray);
        setLayout(null);

        setMiniMap();
        boolean a = true;
        setTabbedPane(a);
//        setUndoRedoPanel();
        setStoragePanel();
//        setOptions();
    }

    public void tabbedPaneReset(){
        tabbedPane.setBrushType(null);
    }

    public void load(){
        try {
            storage.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setOptions()
    {
        options = new Options(120,60, canvas);
        options.setLocation(1150,100);
        add(options);
    }

    public void setStoragePanel()
    {
        storage = new Storage(map, gameFrame,canvas,miniMap);
        storage.setLocation(1000,150);
        storage.setSize(new Dimension(100,50));
        add(storage);
    }

    private void setUndoRedoPanel()
    {
        undoRedoPanel = new UndoRedoPanel(arrangement,100,50);
        undoRedoPanel.setLocation(1000,50);
        add(undoRedoPanel);
    }

    private void setTabbedPane()
    {
        tabbedPane = new SelectionTabbedPane();
        tabbedPane.setSize(420,150);
        tabbedPane.setLocation(500,50);
        add(tabbedPane);
    }

    public void setTabbedPane(boolean a)
    {
        tabbedPane = new SelectionTabbedPane(a);
        tabbedPane.setSize(420,150);
        tabbedPane.setLocation(500,50);
//        add(tabbedPane);
        this.setVisible(false);
        this.setVisible(true);
    }

    public void setMiniMap()
    {
        this.miniMap = new MiniMap(map, canvas, gameFrame);
        miniMap.setLocation(10,50);
        add(miniMap);
    }

    public Options getOptions()
    {
        return options;
    }

    public MiniMap getMiniMap()
    {
        return miniMap;
    }

    public SelectionTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }
}
