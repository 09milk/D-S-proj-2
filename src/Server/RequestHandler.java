package Server;

import Client.DrawActions.IDrawAction;
import Network.ActionType;
import Network.NetworkPackage;
import Network.UserName;

import java.net.Socket;
import java.util.ArrayList;

public class RequestHandler implements Runnable {

    public Socket socket;
    public Room room;
    public UserName userName;
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

    public void linkRoom(String boardName) {
        room = roomManager.getRoom(boardName);
        room.addListener(handlerListener);
    }

    public void unlinkRoom() {
        room.removeListener(handlerListener);
    }

    public void sendCurrentView(){
        serverNetworkController.sendPackage(new NetworkPackage(ActionType.SET_QUEUE, room.actionQueue));
    }


    public class HandlerListener {
        public void newDrawAction(IDrawAction drawAction) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.DRAW, userName, drawAction));
        }

        public void sendAmountOfMember(int amountOfMembers) {
            serverNetworkController.sendPackage(amountOfMembers);
        }

        public void changeLocalName(String name) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.CHANGE_LOCAL_NAME, userName, name));
        }

        public void setQueue(ArrayList<IDrawAction> actionQueue) {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.SET_QUEUE, actionQueue));
        }

        public void newWhiteboard() {
            serverNetworkController.sendPackage(new NetworkPackage(ActionType.NEW_BOARD));
        }

        public UserName getUsername() {
            return userName;
        }
    }
}
