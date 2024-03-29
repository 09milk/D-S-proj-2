package Client;

import Client.DrawActions.IDrawAction;
import Network.ActionType;
import Network.NetworkPackage;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Two queue in this object, localQueue is the local queue that local action will be added here,
 * realQueue is the action queue that will be synchronized between all clients
 */
public class ActionQueue implements Iterable<IDrawAction> {

    private ArrayList<IDrawAction> localQueue = new ArrayList<>();
    private ArrayList<IDrawAction> realQueue = new ArrayList<>();

    private ClientNetworkController clientNetworkController;
    private DrawingPanel drawingPanel;

    private boolean isStartedSyncTimer = false;

    public ActionQueue(ClientNetworkController clientNetworkController, DrawingPanel drawingPanel) {
        this.clientNetworkController = clientNetworkController;
        clientNetworkController.setActionQueue(this);
        this.drawingPanel = drawingPanel;
    }

    public void add(IDrawAction drawAction) {
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.DRAW, drawAction));
        localQueue.add(drawAction);
    }

    public void addRealAction(IDrawAction drawAction) {
        realQueue.add(drawAction);
        startSyncTimer();
    }

    public synchronized void synchronizeRealAndLocal() {
        localQueue = new ArrayList<>(realQueue);
        drawingPanel.repaint();
    }

    private void startSyncTimer() {
        if (!isStartedSyncTimer) {
            isStartedSyncTimer = true;
            new Thread(new SyncTimer()).start();
        }
    }

    @Override
    public Iterator<IDrawAction> iterator() {
        return localQueue.iterator();
    }

    public ArrayList<IDrawAction> getRealQueue() {
        return this.realQueue;
    }

    /**
     * Only NetworkController should call this method, others should call setWhiteBoardView(...)
     *
     * @param realQueue
     */
    public void setRealQueue(ArrayList<IDrawAction> realQueue) {
        this.realQueue = realQueue;
        synchronizeRealAndLocal();
    }

    public void setWhiteBoardView(ArrayList<IDrawAction> realQueue) {
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.SET_QUEUE, realQueue));
    }

    private class SyncTimer implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ignored) {
            }
            isStartedSyncTimer = false;
            synchronizeRealAndLocal();
        }
    }
}
