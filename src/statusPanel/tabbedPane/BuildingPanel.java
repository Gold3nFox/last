package statusPanel.tabbedPane;

import Utils.ImageReader;
import editoR.Editor;
import map.building.BuildingType;

import javax.swing.*;

/**
 * Created by bobvv on 7/9/17.
 */
public class BuildingPanel extends JPanel{
    public BuildingType getSelectedbuilding() {
        return selectedbuilding;
    }

    private BuildingType selectedbuilding = BuildingType.BUILDING1;
    private JButton[] buttons;

    public BuildingPanel(int width, int height)
    {
        setSize(width,height);
        setLayout(null);

        buttons = new JButton[6];
        int i = 0;
        for (BuildingType buildingType: BuildingType.values())
        {
            buttons[i] = new JButton();
            buttons[i].setSize(width/6, height);
            buttons[i].setLocation((i*width)/6, 0);
            buttons[i].setIcon( ImageReader.createImage("/Assets/tabbedPaneIcons/" + buildingType.toString().toLowerCase() + ".png"));
            buttons[i].addActionListener(e -> {
                selectedbuilding = buildingType;
            } );
            add(buttons[i]);
            i++;
        }
    }
}
