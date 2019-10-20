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
                requestHandler.user = networkPackage.user;
                requestHandler.linkRoom(networkPackage.roomName);
                room = requestHandler.room;
                // send member update goes first to wait for chat room creation
                room.sendMemberUpdate();
                requestHandler.sendCurrentViewAndTitle();
                requestHandler.sendChatHistory(false);
                break;
            case DRAW:
                room.addDrawAction(networkPackage.drawAction);
                break;
            case DISCONNECT:
                requestHandler.unlinkRoom();
                room.sendMemberUpdate();
                break;
            case CHANGE_BOARD_NAME:
                room.changeBoardName(networkPackage.boardName);
                break;
            case SET_QUEUE:
                room.setQueue(networkPackage.realQueue);
                break;
            case NEW_BOARD:
                room.newBoard(requestHandler.user);
                room.sendMemberUpdate();
                requestHandler.sendChatHistory(true);
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
