package Server;

import java.io.IOException;
import java.net.Socket;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;

public class ServerNetworkController extends NetworkController {

    private RoomManager roomManager;
    private RequestHandler requestHandler;
    private Room room;

    public ServerNetworkController(RequestHandler requestHandler) {
        this.roomManager = RoomManager.getInstance();
        this.requestHandler = requestHandler;
        Socket socket = requestHandler.socket;
        try {
            this.setIOStream(socket);
        } catch (Exception e) {
            System.out.println("Fail to connect with client(" + socket.getInetAddress().toString() + ")");
        }
        this.startReading();
    }

    @Override
    public void receivedPackage(NetworkPackage networkPackage) {
        super.receivedPackage(networkPackage);
        ActionType actionType = networkPackage.actionType;
        switch (actionType) {
            case CONNECT:
                requestHandler.linkRoom(networkPackage.roomName);
                requestHandler.user = networkPackage.user;
                room = requestHandler.room;
                requestHandler.sendCurrentViewAndTitle();
                break;
            case DRAW:
                room.addDrawAction(networkPackage.drawAction);
                break;
            case DISCONNECT:
                requestHandler.unlinkRoom();
                break;
            case CHANGE_BOARD_NAME:
                room.changeBoardName(networkPackage.boardName);
                break;
            case SET_QUEUE:
                room.setQueue(networkPackage.realQueue);
                break;
            case NEW_BOARD:
                room.newBoard(requestHandler.user);
                break;
            case CHAT:
                room.addChat(networkPackage.user, networkPackage.chatMessage);
                break;
            case CLOSE_ROOM:
                requestHandler.closeRoom();
                break;
            default:
                System.out.println("Unexpected action type: " + actionType.name());
                break;
        }
        this.startReading();
    }

    public void sendPackage(int amountOfMembers) {
        NetworkPackage networkPackage = new NetworkPackage(ActionType.MEMBER_AMOUNT, amountOfMembers);
        startSending(networkPackage);
    }

    @Override
    protected void log(String message) {
        if (room != null) {
            System.out.println(room.roomName + ": " + message);
        } else {
            super.log(message);
        }
    }

    @Override
    protected void networkErrorHandler() {
        try {
            requestHandler.unlinkRoom();
            requestHandler.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
