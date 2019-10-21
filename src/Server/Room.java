package Server;

import Client.DrawActions.IDrawAction;
import Network.User;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Room {
    private final Object setManagerActionLock = new Object();
    public String boardName;
    public ArrayList<IDrawAction> actionQueue = new ArrayList<>();
    public ArrayList<RequestHandler.HandlerListener> listeners = new ArrayList<>();
    public String roomName;
    public ChatHistory chatHistory;
    public ArrayList<User> memberList = new ArrayList<>();
    public HashMap<UUID, ServerNetworkController> usersControllerWaitingForAccept = new HashMap<>();
    private RequestHandler.HandlerListener managerHandlerListener = null;
    private int newFileCount = 0;

    public Room(String roomName) {
        this.roomName = roomName;
        this.chatHistory = new ChatHistory();
        newBoard(null);
    }

    public synchronized void addListener(RequestHandler.HandlerListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(RequestHandler.HandlerListener listener) {
        listeners.remove(listener);
    }

    public void sendMemberUpdate() {
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.sendMemberUpdate(memberList);
        }
    }

    public void sendChatHistory() {
        for (RequestHandler.HandlerListener listener : listeners) {
            listener.sendChatHistory(chatHistory);
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
            if (!listener.getUser().equals(user)) {
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

    public void removeManager() {
        synchronized (setManagerActionLock) {
            managerHandlerListener = null;
        }
        acceptAllUsers();
    }

    public RequestHandler.HandlerListener getManagerHandlerListener() {
        return managerHandlerListener;
    }

    public void setManagerHandlerListener(@NotNull UUID managerUUID) {
        synchronized (setManagerActionLock) {
            if (managerHandlerListener == null) {
                managerHandlerListener = getHandlerListenerByUserUUID(managerUUID);
                if (managerHandlerListener != null) {
                    managerHandlerListener.getUser().isManager = true;
                }
                this.sendMemberUpdate();
            }
        }
    }

    public void closeRoom(User user) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (UUID uuid : usersControllerWaitingForAccept.keySet()) {
            usersControllerWaitingForAccept.get(uuid).requestHandler.handlerListener.closeRoom();
        }
        for (RequestHandler.HandlerListener listener : listeners) {
            if (!listener.getUser().equals(user)) {
                threads.add(listener.closeRoom());
            }
        }
        for (Thread thread : threads) {
            try {
                thread.join(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public RequestHandler.HandlerListener getHandlerListenerByUserUUID(UUID userUUID) {
        for (RequestHandler.HandlerListener listener : listeners) {
            if (listener.getUser().uuid.equals(userUUID)) {
                return listener;
            }
        }
        return null;
    }

    public Boolean hasManager() {
        return managerHandlerListener != null;
    }

    private void acceptAllUsers() {
        for (UUID uuid : usersControllerWaitingForAccept.keySet()) {
            Object lock = usersControllerWaitingForAccept.get(uuid).waitForAcceptLock;
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }
}
