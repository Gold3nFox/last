package map.resource;

import map.terrain.TerrainType;

import java.awt.*;
import java.util.Random;

/**
 * Created by sarb on 6/6/17.
 */
public enum ResourceType
{
    TREE(new Color(34,139,34), TerrainType.GRASS, 2, ".png", 1000, 1),
    PALM(new Color(51,25,0),TerrainType.SAND, 1, ".png", 30, 2),
    DEEP_FISH(new Color(255,0,255),TerrainType.DEEP_WATER, 1, ".gif", 100, 3),
    SHALLOW_FISH(new Color(255,102,102),TerrainType.SHALLOW_WATER, 1, ".gif", 20, 4),
    GOLD(new Color(255,255,0),TerrainType.PEAK, 1, ".png", 100, 5),
    SILVER(new Color(160,160,160),TerrainType.MOUNTAIN, 1, ".png", 100, 6);

    private Color color;
    private TerrainType availableTerrain;
    private int differentType;
    private String extension;
    private int amount;
    private int index;

    public int getIndex() {
        return index;
    }

    ResourceType(Color color, TerrainType terrainType, int differentType, String extension, int amount, int index)
    {
        this.color = color;
        this.availableTerrain = terrainType;
        this.differentType = differentType;
        this.extension = extension;
        this.index = index;
        Random random = new Random(System.currentTimeMillis());
        amount= (int)( (random.nextDouble()+1) * amount );
        this.amount = amount;
    }

    public int getDifferentType()
    {
        return differentType;
    }

    public TerrainType getAvailableTerrain()
    {
        return availableTerrain;
    }

    public Color getColor()
    {
        return color;
    }

    public String getExtension()
    {
        return extension;
    }
}
