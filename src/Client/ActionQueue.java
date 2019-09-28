package Client;

import Client.DrawActions.IDrawAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Two queue in this object, localQueue is the local queue that local action will be added here,
 * realQueue is the action queue that will be synchronized between all clients
 */
public class ActionQueue implements Iterable<IDrawAction>, Serializable {

    private ArrayList<IDrawAction> localQueue = new ArrayList<>();
    private ArrayList<IDrawAction> realQueue = new ArrayList<>();

    private ClientNetworkController clientNetworkController;
    private DrawingPanel drawingPanel;

    public ActionQueue(ClientNetworkController clientNetworkController, DrawingPanel drawingPanel) {
        this.clientNetworkController = clientNetworkController;
        this.clientNetworkController.setActionQueue(this);
        this.drawingPanel = drawingPanel;
    }

    public void add(IDrawAction drawAction) {
        clientNetworkController.sendPackage(drawAction);
        localQueue.add(drawAction);
    }

    public void addRealAction(IDrawAction drawAction) {
        realQueue.add(drawAction);
        //TODO: implement better sync timing
        synchronizeRealAndLocal();
    }

    public synchronized void synchronizeRealAndLocal() {
        localQueue = new ArrayList<>(realQueue);
        drawingPanel.repaint();
    }

    @Override
    public Iterator<IDrawAction> iterator() {
        return localQueue.iterator();
    }

    public ArrayList<IDrawAction> getRealQueue(){
        return this.realQueue;
    }

    public void setRealQueue(ArrayList<IDrawAction> realQueue) {
        this.realQueue = realQueue;
        synchronizeRealAndLocal();
    }
}
