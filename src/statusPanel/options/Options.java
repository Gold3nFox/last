package statusPanel.options;

import mainFrame.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sarb on 7/10/17.
 */
public class Options extends JPanel
{
    private int width;
    private int height;

    private JCheckBox coordinate;
    private JCheckBox simpleDraw;

    private JLabel coordLabel;
    private JLabel simpleLabel;

    private boolean isSimple;
    private boolean isCoordinated;

    public Options(int width, int height, mainFrame.Canvas canvas)
    {
        isCoordinated = true;
        isSimple = false;

        setSize(width,height);
        setLayout(null);

        coordinate = new JCheckBox();
        coordLabel = new JLabel("Coordinated : ");
        coordLabel.setLabelFor(coordinate);
        coordinate.addActionListener(e->
        {
            isCoordinated = !isCoordinated;
            canvas.setCoordinate(isCoordinated);
            canvas.repaintCanvas();
        });
        coordLabel.setLocation(10,10);
        coordLabel.setSize(100,20);
        coordinate.setLocation(90,10);
        coordinate.setSize(20,20);

        simpleDraw = new JCheckBox();
        simpleLabel = new JLabel("Simple Draw : ");
        simpleLabel.setLabelFor(simpleDraw);
        simpleDraw.addActionListener(e->
        {
            isSimple = !isSimple;
            canvas.setSimple(isSimple);
            canvas.repaintCanvas();
        });
        simpleLabel.setLocation(10,35);
        simpleLabel.setSize(100,20);
        simpleDraw.setLocation(90,35);
        simpleDraw.setSize(20,20);

        add(simpleDraw);
        add(coordinate);
        add(simpleLabel);
        add(coordLabel);
    }

}
