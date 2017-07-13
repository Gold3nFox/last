package statusPanel.tabbedPane;

import editoR.Editor;
import map.building.BuildingType;
import map.resource.ResourceType;
import map.terrain.TerrainType;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SelectionTabbedPane extends JTabbedPane
{
    private TerrainPanel terrainPanel;
    private ResourcePanel resourcePanel;

    private BuildingPanel buildingPanel;

    public void setBrushType(Editor.BrushType brushType)
    {
        this.brushType = brushType;
    }

    private Editor.BrushType brushType = Editor.BrushType.TERRAIN;

    public SelectionTabbedPane()
    {
        this.setBackground(Color.black);
        terrainPanel = new TerrainPanel(400,100);
        resourcePanel = new ResourcePanel(400,100);
        buildingPanel = new BuildingPanel(400,100);
        this.setBounds(50,50,420,150);
        this.add("Terrain",terrainPanel);
        this.add("Resource",resourcePanel);
        this.add("Building",buildingPanel);

        ChangeListener changeListener = changeEvent ->
        {
            JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            if(sourceTabbedPane.getTitleAt(index).equals("Terrain"))
                brushType = Editor.BrushType.TERRAIN;
            else if(sourceTabbedPane.getTitleAt(index).equals("Resource"))
                brushType = Editor.BrushType.RESOURCE;
            else
                brushType = Editor.BrushType.BUILDING;
        };

        this.addChangeListener(changeListener);
    }
    public SelectionTabbedPane(boolean a)
    {
        brushType = Editor.BrushType.BUILDING;

        buildingPanel = new BuildingPanel(400,100);
        this.setBounds(50,50,420,150);

        this.add("Building",buildingPanel);

        ChangeListener changeListener = changeEvent ->
        {
            JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            brushType = Editor.BrushType.BUILDING;
        };

        this.addChangeListener(changeListener);
    }

    public ResourceType getResource()
    {
        return resourcePanel.getSelectedResource();
    }

    public TerrainType getTerrain()
    {
        return terrainPanel.getSelectedTerrain();
    }

    public BuildingType getBuilding() {
        return buildingPanel.getSelectedbuilding();
    }

    public Editor.BrushType getBrushType()
    {
        return brushType;
    }
}
