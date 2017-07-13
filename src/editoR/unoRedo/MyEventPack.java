package editoR.unoRedo;


import mainFrame.Observable;

import java.util.Vector;

public class
MyEventPack {

    private Vector<TerrainPlacedEvent> terrainChanges;
    private Vector<Observable> removedObservables;

    public MyEventPack() {
        this.terrainChanges = new Vector<>();
        this.removedObservables = new Vector<>();
    }

    public void addEvent(TerrainPlacedEvent myEvent)
    {
        terrainChanges.add(myEvent);
    }

    public void addObservable(Observable observable)
    {
        removedObservables.add(observable);
    }

    public Vector<TerrainPlacedEvent> getTerrainChanges()
    {
        return terrainChanges;
    }

    public Vector<Observable> getRemovedObservables() {
        return removedObservables;
    }
}
