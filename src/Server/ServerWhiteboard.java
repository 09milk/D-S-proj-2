package Server;

import Client.DrawActions.IDrawAction;

import java.util.ArrayList;

public class ServerWhiteboard {
    public ArrayList<IDrawAction> actionQueue = new ArrayList<>();
    public ArrayList<RequestHandler.HandlerListener> listeners = new ArrayList<>();

    public void addDrawAction(IDrawAction drawAction) {
        actionQueue.add(drawAction);
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.newDrawAction(drawAction);
        }
    }

    public void addListener(RequestHandler.HandlerListener listener) {
        listeners.add(listener);
        sendAmountOfMember();
    }

    public void removeListener(RequestHandler.HandlerListener listener) {
        listeners.remove(listener);
        sendAmountOfMember();
    }

    private void sendAmountOfMember() {
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.sendAmountOfMember(listeners.size());
        }
    }
}
