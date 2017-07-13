package editoR.unoRedo;

import map.terrain.Tile;

public class TerrainPlacedEvent
{
    private Tile oldTile;
    private Tile newTile;

    public TerrainPlacedEvent(Tile oldTile, Tile newTile)
    {
        this.oldTile = oldTile;
        this.newTile = newTile;
    }

    public Tile getOldTile()
    {
        return oldTile;
    }

    public Tile getNewTile()
    {
        return newTile;
    }
}
