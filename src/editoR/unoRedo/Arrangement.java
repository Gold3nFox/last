package editoR.unoRedo;


import editoR.Editor;
import map.Map;
import mainFrame.Observable;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;

public class Arrangement
{

    private Stack<MyEventPack> undo;
    private Stack<MyEventPack> redo;
    private MyEventPack eventCache;

    private Map map;
    private Editor editor;

    public Arrangement()
    {
        eventCache = new MyEventPack();
        undo = new Stack<>();
        redo = new Stack<>();
    }

    private void surfAPack(MyEventPack eventPack, ActionType actionType)
    {
        Vector <TerrainPlacedEvent> events = eventPack.getTerrainChanges();

        switch (actionType)
        {
            case UNDO:

                for (int i = events.size()-1; i >= 0 ; i--)
                {
                    TerrainPlacedEvent event = events.get(i);
                    editor.placeTerrainFromArrangement(event.getOldTile().getTerrainType(), event.getOldTile().getX(), event.getOldTile().getY());
                }

                if (eventPack.getRemovedObservables().size() == 1 )
                {
                    Observable observable = eventPack.getRemovedObservables().get(0);

                    map.getTile(observable.getX(),observable.getY()).setFiller(null);
                    return;
                }

                for (Observable observable: eventPack.getRemovedObservables())
                {
                    map.getTile(observable.getX(),observable.getY()).setFiller(observable);
                }

                break;
                case REDO:
                for (int i = 0; i <events.size() ; i++)
                {
                    TerrainPlacedEvent event = events.get(i);
                    editor.placeTerrainFromArrangement(event.getNewTile().getTerrainType(), event.getNewTile().getX(), event.getNewTile().getY());
                }

                if (eventPack.getRemovedObservables().size() == 1 )
                {
                    Observable observable = eventPack.getRemovedObservables().get(0);
                    map.getTile(observable.getX(),observable.getY()).setFiller(observable);
                    return;
                }

                for (Observable observable: eventPack.getRemovedObservables())
                {
                    map.getTile(observable.getX(),observable.getY()).setFiller(null);
                }

                break;
        }
    }

    public void pushObservableEvent(Observable observable)
    {
        eventCache.addObservable(observable);
    }

    public void pushTerrainPlacedEvent(TerrainPlacedEvent e)
    {
        eventCache.addEvent(e);
    }

    public void closeEventCache()
    {
        undo.push(this.eventCache);
        this.eventCache = new MyEventPack();
    }

    public void undo()
    {
        try
        {
            MyEventPack tempEventPack = undo.pop();
            redo.push(tempEventPack);
            surfAPack(tempEventPack,ActionType.UNDO);
            editor.getCanvasPainter().repaintCanvas();
        }catch (EmptyStackException e){
        }
        map.updateAll();
    }

    public void redo()
    {
        try
        {
            MyEventPack tempEventPack = redo.pop();
            undo.push(tempEventPack);
            surfAPack(tempEventPack,ActionType.REDO);
            editor.getCanvasPainter().repaintCanvas();
        }catch (EmptyStackException e){
        }
        map.updateAll();
    }

    public void clearRedo()
    {
        redo.clear();
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    public void setEditor(Editor editor)
    {
        this.editor = editor;
    }
}
