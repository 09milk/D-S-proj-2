package Server;

import Client.DrawActions.IDrawAction;
import Network.ActionType;
import Network.NetworkPackage;
import Network.User;

import java.net.Socket;
import java.util.ArrayList;

public class RequestHandler implements Runnable {

    public Socket socket;
    public Room room;
    public User user;
    public HandlerListener handlerListener = new HandlerListener();
    private RoomManager roomManager;
    private ServerNetworkController serverNetworkController;

    public RequestHandler(Socket socket) {
        this.socket = socket;
        this.roomManager = RoomManager.getInstance();
    }

    @Override
    public void run() {
        serverNetworkController = new ServerNetworkController(this);
    }

    public void linkRoom(Room room) {
        this.room = room;
        room.addListener(handlerListener);
        room.memberList.add(user);
    }

    public void unlinkRoom() {
        room.removeListener(handlerListener);
        room.memberList.remove(user);
        if (user.isManager) {
            room.removeManager();
        }
    }

    public void sendCurrentViewAndTitle() {
        serverNetworkController.sendPackage(new NetworkPackage(ActionType.SET_QUEUE, room.actionQueue));
        serverNetworkController.sendPackage(new NetworkPackage(ActionType.CHANGE_BOARD_NAME, null, room.boardName));
    }

    public void sendChatHistory(boolean toAll) {
        if (toAll)
            room.sendChatHistory();
        else
            serverNetworkController.sendPackage(new NetworkPackage(room.chatHistory));
    }

    public void closeRoom() {
        roomManager.closeRoom(user, room);
    }


    public class HandlerListener {
        public void newDrawAction(IDrawAction drawAction) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.DRAW, user, drawAction));
        }

        public void sendMemberUpdate(ArrayList<User> memberList) {
            serverNetworkController.sendPackage(new NetworkPackage(memberList));
        }

        public void sendChatHistory(ChatHistory chatHistory) {
            serverNetworkController.sendPackage(new NetworkPackage(chatHistory));
        }

        public void changeBoardName(String name) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.CHANGE_BOARD_NAME, null, name));
        }

        public void setQueue(ArrayList<IDrawAction> actionQueue) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.SET_QUEUE, actionQueue));
        }

        public void newWhiteboard() {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.NEW_BOARD));
        }

        public void addChat(User user, String chatMessage) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.CHAT, user, chatMessage));
        }

        public User getUser() {
            return user;
        }

        public void askForAcceptFromManager(User user) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.ACCEPT_USER, user));
        }

        public Thread closeRoom() {
            return serverNetworkController.sendPackage(new NetworkPackage(ActionType.CLOSE_ROOM));
        }
    }
}
