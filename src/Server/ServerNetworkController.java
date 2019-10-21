package Server;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;

import java.io.IOException;
import java.net.Socket;

public class ServerNetworkController extends NetworkController {

    public final Object waitForAcceptLock = new Object();
    public RequestHandler requestHandler;
    private RoomManager roomManager;
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
                room = roomManager.getRoom(networkPackage.roomName);
                if (room.hasManager()) {
                    room.usersControllerWaitingForAccept.put(networkPackage.user.uuid, this);
                    room.getManagerHandlerListener().askForAcceptFromManager(networkPackage.user);
                    this.startReading();
                    synchronized (waitForAcceptLock) {
                        try {
                            waitForAcceptLock.wait();
                            room.usersControllerWaitingForAccept.remove(networkPackage.user.uuid);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
                requestHandler.linkRoom(room);
                // send member update goes first to wait for chat room creation
                room.sendMemberUpdate();
                requestHandler.sendCurrentViewAndTitle();
                requestHandler.sendChatHistory(false);
                break;
            case DRAW:
                room.addDrawAction(networkPackage.drawAction);
                break;
            case DISCONNECT:
                networkErrorHandler();
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
            case SET_MANAGER:
                room.setManagerHandlerListener(networkPackage.user.uuid);
                break;
            case KICK_USER:
                room.getHandlerListenerByUserUUID(networkPackage.user.uuid).closeRoom();
                break;
            case CLOSE_ROOM:
                requestHandler.closeRoom();
                break;
            case ACCEPT_USER:
                Object lock = room.usersControllerWaitingForAccept.get(networkPackage.user.uuid).waitForAcceptLock;
                synchronized (lock) {
                    lock.notifyAll();
                }
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
            room.sendMemberUpdate();
            requestHandler.socket.close();
        } catch (IOException ignored) {
        }
    }
}
