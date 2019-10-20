package Server;

import Client.DrawActions.IDrawAction;
import Network.User;

import java.util.ArrayList;

public class Room {
    public String boardName;
    public ArrayList<IDrawAction> actionQueue = new ArrayList<>();
    public ArrayList<RequestHandler.HandlerListener> listeners = new ArrayList<>();
    public String roomName;
    public ChatHistory chatHistory;
    public ArrayList<User> memberList = new ArrayList<>();
    private int newFileCount = 0;

    public Room(String roomName) {
        this.roomName = roomName;
        this.chatHistory = new ChatHistory();
        newBoard(null);
    }

    public synchronized void addListener(RequestHandler.HandlerListener listener, User user) {
        listeners.add(listener);
        memberList.add(user);
        sendMemberUpdate();
    }

    public synchronized void removeListener(RequestHandler.HandlerListener listener, User user) {
        listeners.remove(listener);
        memberList.remove(user);
        sendMemberUpdate();
    }

    private void sendMemberUpdate() {
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.sendMemberUpdate(memberList);
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

    public void newBoard(User user) {
        this.actionQueue = new ArrayList<>();
        for (RequestHandler.HandlerListener listener : listeners) {
            if (!listener.getUsername().equals(user)) {
                listener.newWhiteboard();
            }
        }
        changeBoardName("new " + newFileCount);
        newFileCount++;
    }

    public void addChat(User user, String chatMessage) {
        chatHistory.addChat(user, chatMessage);
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.addChat(user, chatMessage);
        }
    }

    public void closeRoom(User user) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (RequestHandler.HandlerListener listener : listeners) {
            if (!listener.getUsername().equals(user)) {
                threads.add(listener.closeRoom());
            }
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
