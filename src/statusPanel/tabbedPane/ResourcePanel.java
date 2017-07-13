package statusPanel.tabbedPane;

import Utils.ImageReader;
import map.resource.*;
import javax.swing.*;

/**
 * Created by bobvv on 6/8/17.
 */
public class ResourcePanel extends JPanel
{
    private ResourceType selectedResource = ResourceType.TREE;
    private JButton[] buttons;

    public ResourcePanel(int width, int height)
    {
        setSize(width,height);
        setLayout(null);

        buttons = new JButton[6];
        int i = 0;
        for (ResourceType resourceType: ResourceType.values())
        {
            buttons[i] = new JButton();
            buttons[i].setSize(width/6, height);
            buttons[i].setLocation((i*width)/6, 0);
            buttons[i].setIcon( ImageReader.createImage("/Assets/tabbedPaneIcons/" + resourceType.toString().toLowerCase() + ".png"));
            buttons[i].addActionListener(e -> selectedResource = resourceType );
            add(buttons[i]);
            i++;
        }

    }

    public ResourceType getSelectedResource() {
        return selectedResource;
    }
}
