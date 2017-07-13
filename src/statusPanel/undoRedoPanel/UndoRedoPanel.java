package statusPanel.undoRedoPanel;

import Utils.ImageReader;
import editoR.unoRedo.Arrangement;

import javax.swing.*;

/**
 * Created by sarb on 7/8/17.
 */
public class UndoRedoPanel extends JPanel
{
    private int width,height;

    private JButton undo,redo;
    private Arrangement arrangement;

    public UndoRedoPanel(Arrangement arrangement, int width, int height)
    {
        this.width = width;
        this.height = height;

        this.arrangement = arrangement;

        setSize(width,height);
        setLayout(null);

        undo = new JButton();
        undo.setSize((width/2)-2 , height-2);
        undo.setLocation(1,1);
        undo.setIcon(ImageReader.createImage("/Assets/undoRedoIcons/undo.png"));
        undo.addActionListener(e-> arrangement.undo());
        add(undo);

        redo = new JButton();
        redo.setSize((width/2)-2 , height-2);
        redo.setLocation(width/2,1);
        redo.setIcon(ImageReader.createImage("/Assets/undoRedoIcons/redo.png"));
        redo.addActionListener(e-> arrangement.redo());
        add(redo);

    }

}
