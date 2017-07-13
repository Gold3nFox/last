package editoR;

import ERFAN.Panel.MarketPanel;
import ERFAN.Panel.WorkerPanel;
import editoR.unoRedo.Arrangement;
import editoR.unoRedo.TerrainPlacedEvent;
import mainFrame.CanvasInterface;
import mainFrame.CanvasPainter;
import mainFrame.MouseDragListener;
import mainFrame.MousePressListener;
import map.Map;
import map.building.Building;
import map.building.BuildingType;
import map.resource.Resource;
import map.resource.ResourceType;
import map.terrain.TerrainType;
import map.terrain.Tile;
import statusPanel.BottomPanel;
import statusPanel.BottomPanelTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by sarb on 5/29/17.
 */
public class Editor implements MouseDragListener, MousePressListener
{

    private Map map;
    private Arrangement arrangement;
    private BottomPanel bottomPanel;
    private BottomPanelTest bottomPanelTest;
    private CanvasPainter canvasPainter;
    private CanvasInterface canvasInterface;
    private WorkerPanel wp = null;
    private MarketPanel mp = null;

    private int[][][] neighbors8 = { {{-1, -1}, {-1, 1}, {0, -1}, {0, -2}, {1, 0}, {0, 2}, {0, 1}, {-1, 0}} , {{1, -1}, {1, 1}, {0, -1}, {0, -2}, {1, 0}, {0, 2}, {0, 1}, {-1,0}} };
    private int[][][] neighbors4 = { {{-1, -1}, {0, -1}, {0, 1}, {-1, 1}} , {{0, -1}, {1, -1}, {1, 1}, {0, 1}}};


    public Editor(Map map, Arrangement arrangement)
    {
        this.map = map;
        this.arrangement = arrangement;
    }

    private boolean checkInBoard(int x, int y)
    {
        if (x >= 0 && x < map.getWidthCoord() && y >= 0 && y < map.getHeightCoord())
            return true;
        return false;
    }

    private boolean placeTerrain(TerrainType terrain, int x, int y)
    {
        if (map.getTile(x, y).getTerrainType() == terrain)
            return false;

        TerrainType oldTerrainType = map.getTile(x, y).getTerrainType();

        if (map.getTile(x, y).getFiller() != null && !(map.getTile(x, y).getFiller()).isAvailableTerrain(terrain))
        {
            arrangement.pushObservableEvent(map.getTile(x,y).getFiller());
            map.getTile(x, y).setFiller(null);
        }

        int bin = y%2;
        for (int i = 0; i < 8; i++)
        {
            int xx = x + neighbors8[bin][i][0];
            int yy = y + neighbors8[bin][i][1];
            if (!checkInBoard(xx,yy))
                continue;

            Tile tile = map.getTile(xx,yy);
            if (tile.getTerrainType().getIndex() < terrain.getIndex() - 1)
                placeTerrain(TerrainType.getTerrain(terrain.getIndex() - 1), xx, yy);
            else if (tile.getTerrainType().getIndex() > terrain.getIndex() + 1)
                placeTerrain(TerrainType.getTerrain(terrain.getIndex() + 1), xx, yy);
        }
        map.getTile(x, y).setTerrainType(terrain);

        TerrainPlacedEvent terrainPlacedEvent = new TerrainPlacedEvent(new Tile(oldTerrainType, x, y,map), map.getTile(x,y));
        arrangement.pushTerrainPlacedEvent(terrainPlacedEvent);

        return true;
    }

    private boolean placeResource(ResourceType resourceType, int x, int y)
    {
        if (resourceType.getAvailableTerrain() != map.getTile(x, y).getTerrainType())
            return false;
        if (map.getTile(x, y).getFiller() != null && map.getTile(x,y).getFiller() instanceof Resource && ((Resource) (map.getTile(x, y).getFiller())).getResourceType() == resourceType)
            return false;

        Resource resource = new Resource(resourceType, x, y);

        arrangement.pushObservableEvent(resource);
        map.getTile(x, y).setFiller(resource);
        return true;
    }

    public boolean placeBuilding(BuildingType buildingType,int x,int y)
    {
        if (map.getTile(x, y).getFiller() != null && map.getTile(x,y).getFiller() instanceof Building && ((Building)(map.getTile(x, y).getFiller())).getBuildingType() == buildingType)
        {
            System.out.println(1);
            return false;
        }
        if (buildingType.getAvailableTerrain() != map.getTile(x, y).getTerrainType())
        {
            System.out.println(buildingType.getAvailableTerrain() + " " + map.getTile(x,y).getTerrainType());
            System.out.println(x + " ... " + y);
            return false;
        }
        if (buildingType == BuildingType.BUILDING3) {
            if (map.getTile(x, y).getFiller() instanceof Resource && ((Resource) (map.getTile(x, y).getFiller())).getResourceType() != ResourceType.SILVER)
                return false;
            else if (!(map.getTile(x, y).getFiller() instanceof Resource))
                return false;
        }
        Building building = new Building(buildingType, x, y);


        arrangement.pushObservableEvent(building);
        map.getTile(x, y).setFiller(building);
        return true;
    }

    public void placeTerrainFromArrangement(TerrainType terrain, int x, int y)
    {
        map.getTile(x, y).setTerrainType(terrain);
        map.getTile(x, y).update();
    }

    private boolean isHouseSelected = false;

    public void place(MouseEvent e)
    {
        int x = e.getX() / canvasInterface.getTilesize();
        int y = (e.getY() * 2 * canvasInterface.getCotang()) / canvasInterface.getTilesize();

        int xtile = canvasInterface.getXRoot() + x;
        int ytile = canvasInterface.getYRoot() + y;

        if (bottomPanel == null && SwingUtilities.isLeftMouseButton(e))
        {
            if(map.getTile(xtile,ytile).getFiller() instanceof Building && ((Building) map.getTile(xtile,ytile).getFiller()).getBuildingType() == BuildingType.BUILDING1 ) {
                wp = new WorkerPanel(400, 100, 500, 50, ((Building) map.getTile(xtile, ytile).getFiller()).getBuildingType().getHealth(), ((Building) map.getTile(xtile, ytile).getFiller()).getBuildingType().getMaxhealth(), "warriorhouse", map,canvasInterface,xtile,ytile, bottomPanelTest,bottomPanelTest.getTabbedPane(), this);
                bottomPanelTest.removeAll();
                bottomPanelTest.setMiniMap();
                bottomPanelTest.add(wp);
                isHouseSelected = true;
//                wp.repaint();
            }else if(isHouseSelected == true && wp != null && !(map.getTile(xtile,ytile).getFiller() instanceof Building)) {
                bottomPanelTest.removeAll();
                bottomPanelTest.setMiniMap();
                wp = null;
                isHouseSelected = false;
            }else if(isHouseSelected == true && wp == null)
            {
                bottomPanelTest.removeAll();
                bottomPanelTest.setMiniMap();
                isHouseSelected = false;
            }

            bottomPanelTest.setVisible(false);
            bottomPanelTest.setVisible(true);

            Point point = e.getPoint();

            boolean isValid = false;
            int bin = y%2;
            for (int i = 0; i < 8; i++)
            {
                int xx = xtile + neighbors8[bin][i][0];
                int yy = ytile + neighbors8[bin][i][1];
                if (checkInBoard(xx, yy) && map.getTile(xx,yy).getPolygon(canvasInterface.getXRoot(), canvasInterface.getYRoot(), canvasInterface.getTilesize(),canvasInterface.getCotang()).contains(point))
                {
                    xtile = xx;
                    ytile = yy;
                    isValid = true;
                }
            }
            if (!isValid)
            {
                int xx = xtile;
                int yy = ytile;
                if (checkInBoard(xx, yy) && map.getTile(xx,yy).getPolygon(canvasInterface.getXRoot(), canvasInterface.getYRoot(), canvasInterface.getTilesize(),canvasInterface.getCotang()).contains(point))
                {
                    xtile = xx;
                    ytile = yy;
                    isValid = true;
                }
            }
            if (!isValid)
                return;

            System.out.println(bottomPanelTest.getTabbedPane().getBrushType()  + "-----");
            if ( bottomPanelTest.getTabbedPane().getBrushType() == BrushType.BUILDING && map.getGold() >= 50)
                    if (placeBuilding(bottomPanelTest.getTabbedPane().getBuilding(), xtile, ytile))
                    {
                        map.setGold(map.getGold() - 50);
                        System.out.println(map.getGold());
                        arrangement.closeEventCache();
                        arrangement.clearRedo();
                    }

                    bottomPanelTest.getTabbedPane().setBrushType(BrushType.RESOURCE);
            canvasPainter.repaintCanvas();
            return;
        }
        if (bottomPanel == null)
            return;
//        int x = e.getX() / canvasInterface.getTilesize();
//        int y = (e.getY() * 2 * canvasInterface.getCotang()) / canvasInterface.getTilesize();
//
//        int xtile = canvasInterface.getXRoot() + x;
//        int ytile = canvasInterface.getYRoot() + y;

        Point point = e.getPoint();

        boolean isValid = false;
        int bin = y%2;

        for (int i = 0; i < 8; i++)
        {
            int xx = xtile + neighbors8[bin][i][0];
            int yy = ytile + neighbors8[bin][i][1];
            if (checkInBoard(xx, yy) && map.getTile(xx,yy).getPolygon(canvasInterface.getXRoot(), canvasInterface.getYRoot(), canvasInterface.getTilesize(),canvasInterface.getCotang()).contains(point))
            {
                xtile = xx;
                ytile = yy;
                isValid = true;
            }
        }
        if (!isValid)
        {
            int xx = xtile;
            int yy = ytile;
            if (checkInBoard(xx, yy) && map.getTile(xx,yy).getPolygon(canvasInterface.getXRoot(), canvasInterface.getYRoot(), canvasInterface.getTilesize(),canvasInterface.getCotang()).contains(point))
            {
                xtile = xx;
                ytile = yy;
                isValid = true;
            }
        }

        if (!isValid)
            return;

        switch (bottomPanel.getTabbedPane().getBrushType())
        {
            case RESOURCE:
                if (placeResource(bottomPanel.getTabbedPane().getResource(), xtile, ytile))
                {
                    arrangement.closeEventCache();
                    arrangement.clearRedo();
                }
                break;
            case TERRAIN:
                if (placeTerrain(bottomPanel.getTabbedPane().getTerrain(), xtile, ytile))
                {
                    arrangement.closeEventCache();
                    arrangement.clearRedo();
                    map.updateAll();
                }
                break;
            case BUILDING:
                if (placeBuilding(bottomPanel.getTabbedPane().getBuilding(), xtile, ytile))
                {
                    arrangement.closeEventCache();
                    arrangement.clearRedo();
                }
        }

        canvasPainter.repaintCanvas();
    }

    public CanvasPainter getCanvasPainter()
    {
        return canvasPainter;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
//        place(e);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
//        place(e);
    }

    public enum BrushType
    {
        RESOURCE,
        TERRAIN,
        BUILDING

    }

    public void setCanvasPainter(CanvasPainter canvasPainter)
    {
        this.canvasPainter = canvasPainter;
    }

    public void setCanvasInterface(CanvasInterface canvasInterface)
    {
        this.canvasInterface = canvasInterface;
    }

    public void setBottomPanel(BottomPanel bottomPanel)
    {
        this.bottomPanel = bottomPanel;
    }

    public void setBottomPanelTest(BottomPanelTest bottomPanelTest)
    {
        this.bottomPanelTest = bottomPanelTest;
    }

    public BottomPanel getBottomPanel()
    {

        return bottomPanel;
    }
}
