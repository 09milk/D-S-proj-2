package Server;

import java.net.Socket;
import java.util.ArrayList;

import Client.DrawActions.IDrawAction;
import Network.ActionType;
import Network.NetworkPackage;
import Network.User;

public class RequestHandler implements Runnable {

    public Socket socket;
    public Room room;
    public User user;
    private RoomManager roomManager;
    private ServerNetworkController serverNetworkController;
    private HandlerListener handlerListener = new HandlerListener();

    public RequestHandler(Socket socket) {
        this.socket = socket;
        this.roomManager = RoomManager.getInstance();
    }

    @Override
    public void run() {
        serverNetworkController = new ServerNetworkController(this);
    }

    public void linkRoom(String roomName) {
        room = roomManager.getRoom(roomName);
        room.addListener(handlerListener);
    }

    public void unlinkRoom() {
        room.removeListener(handlerListener);
    }

    public void sendCurrentViewAndTitle() {
        serverNetworkController.sendPackage(new NetworkPackage(ActionType.SET_QUEUE, room.actionQueue));
        serverNetworkController.sendPackage(new NetworkPackage(ActionType.CHANGE_BOARD_NAME, null, room.boardName));
        // also send the chat history
        serverNetworkController.sendPackage(new NetworkPackage(ActionType.CHAT_HISTORY, room.chatHistory));
    }

    public void closeRoom() {
        roomManager.closeRoom(user, room);
    }


    public class HandlerListener {
        public void newDrawAction(IDrawAction drawAction) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.DRAW, user, drawAction));
        }

        public void sendAmountOfMember(int amountOfMembers) {
            serverNetworkController.sendPackage(amountOfMembers);
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

        public User getUsername() {
            return user;
        }

        public Thread closeRoom() {
            return serverNetworkController.sendPackage(new NetworkPackage(ActionType.CLOSE_ROOM));
        }
    }
}
