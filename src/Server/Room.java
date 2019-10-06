package Server;

import Client.DrawActions.IDrawAction;
import Network.UserName;

import java.util.ArrayList;

public class Room {
    public String boardName;
    public ArrayList<IDrawAction> actionQueue = new ArrayList<>();
    public ArrayList<RequestHandler.HandlerListener> listeners = new ArrayList<>();
    public String roomName;
    private int newFileCount = 0;

    public Room(String roomName) {
        this.roomName = roomName;
        newBoard(null);
    }

    public synchronized void addListener(RequestHandler.HandlerListener listener) {
        listeners.add(listener);
        sendAmountOfMember();
    }

    public synchronized void removeListener(RequestHandler.HandlerListener listener) {
        listeners.remove(listener);
        sendAmountOfMember();
    }

    private void sendAmountOfMember() {
        System.out.println(roomName + ": " + "Current number of member(s): " + listeners.size());
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.sendAmountOfMember(listeners.size());
        }
    }

    public void addDrawAction(IDrawAction drawAction) {
        actionQueue.add(drawAction);
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.newDrawAction(drawAction);
        }
    }

    public void changeBoardName(String name) {
        boardName = name;
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.changeBoardName(boardName);
        }
    }

    public void setQueue(ArrayList<IDrawAction> actionQueue) {
        System.out.println("Sending the whole queue thing!");
        this.actionQueue = actionQueue;
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.setQueue(actionQueue);
        }
    }

    public void newBoard(UserName userName) {
        this.actionQueue = new ArrayList<>();
        for (RequestHandler.HandlerListener listener : listeners) {
            if (!listener.getUsername().equals(userName)) {
                listener.newWhiteboard();
            }
        }
        changeBoardName("new " + newFileCount);
        newFileCount++;
    }
}
