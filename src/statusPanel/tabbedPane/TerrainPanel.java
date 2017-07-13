package statusPanel.tabbedPane;

import Utils.ImageReader;
import map.terrain.TerrainType;
import javax.swing.*;

/**
 * Created by bobvv on 6/8/17.
 */
public class TerrainPanel extends JPanel
{
    private TerrainType selectedTerrain = TerrainType.GRASS;
    private JButton[] buttons;

    public TerrainPanel(int width, int height)
    {
        setSize(width,height);
        setLayout(null);

        buttons = new JButton[6];
        int i = 0;
        for (TerrainType terrainType: TerrainType.values())
        {
            buttons[i] = new JButton();
            buttons[i].setSize(width/6, height);
            buttons[i].setLocation((i*width)/6, 0);
            buttons[i].setIcon( ImageReader.createImage("/Assets/tabbedPaneIcons/" + terrainType.toString().toLowerCase() + ".png"));
            buttons[i].addActionListener(e -> selectedTerrain = terrainType );
            add(buttons[i]);
            i++;
        }
    }

    public TerrainType getSelectedTerrain()
    {
        return selectedTerrain;
    }
}
