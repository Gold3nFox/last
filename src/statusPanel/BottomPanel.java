package statusPanel;

import editoR.Editor;
import editoR.unoRedo.Arrangement;
import mainFrame.Canvas;
import mainFrame.MainFrame;
import map.Map;
import saving.Storage;
import statusPanel.miniMap.MiniMap;
import statusPanel.options.Options;
import statusPanel.tabbedPane.SelectionTabbedPane;
import statusPanel.undoRedoPanel.UndoRedoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bobvv on 5/30/17.
 */
public class BottomPanel extends JPanel
{
    private Map map;
    private Canvas canvas;
    private Editor editor;
    private Arrangement arrangement;
    private MainFrame mainFrame;

    private MiniMap miniMap;
    private SelectionTabbedPane tabbedPane;
    private UndoRedoPanel undoRedoPanel;
    private Storage storage;
    private Options options;


    public BottomPanel(int width, int height, Map map, Canvas canvas, Editor editor, Arrangement arrangement, MainFrame mainFrame)
    {
        this.map = map;
        this.canvas = canvas;
        this.editor = editor;
        this.arrangement = arrangement;
        this.mainFrame = mainFrame;

        setSize(width, height);
        setBackground(Color.darkGray);
        setLayout(null);

        setMiniMap();
        setTabbedPane();
        setUndoRedoPanel();
        setStoragePanel();
        setOptions();
    }

    private void setOptions()
    {
        options = new Options(120,60, canvas);
        options.setLocation(1100,100);
        add(options);
    }

    private void setStoragePanel()
    {
        storage = new Storage(map, mainFrame,canvas,miniMap);
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

    private void setMiniMap()
    {
        this.miniMap = new MiniMap(map, canvas, mainFrame);
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
