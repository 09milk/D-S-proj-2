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

    public ActionQueue(ClientNetworkController clientNetworkController) {
        this.clientNetworkController = clientNetworkController;
        this.clientNetworkController.setActionQueue(this);
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
    }

    @Override
    public Iterator<IDrawAction> iterator() {
        return localQueue.iterator();
    }
}
