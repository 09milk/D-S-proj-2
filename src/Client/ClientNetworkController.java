package Client;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;
import Network.User;
import Server.ChatHistory;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkController extends NetworkController {

    private final Object actionQueueActionLock = new Object();
    private final Object whiteboardClientActionLock = new Object();
    public User user;
    private String address;
    private int port;
    private ActionQueue actionQueue;
    private WhiteboardClient whiteboardClient;


    public ClientNetworkController(String address, int port, String roomName, User user) {
        this.address = address;
        this.port = port;
        this.roomName = roomName;
        this.user = user;
    }

    public void startCommunication() throws Exception {
        Socket socket;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            throw new Exception("Failed to start socket");
        }
        this.setIOStream(socket);

        sendPackage(new NetworkPackage(ActionType.CONNECT, user, this.roomName));

        this.startReading();
    }

    @Override
    public void receivedPackage(NetworkPackage networkPackage) {
        super.receivedPackage(networkPackage);
        ActionType actionType = networkPackage.actionType;
        switch (actionType) {
            case DRAW:
                actionQueue.addRealAction(networkPackage.drawAction);
                break;
            case MEMBER_UPDATE:
                boolean success = false;
                while (!success) {
                    try {
                        whiteboardClient.whiteboardClientGUI.chatRoom.updateMemberList(networkPackage.memberList);
                        String text = String.format(ClientConfig.CHAT_ROOM_STRING, networkPackage.memberList.size());
                        whiteboardClient.whiteboardClientGUI.btnChatRoom.setText(text);
                        success = true;
                    } catch (Exception e) {
                    }
                }
                break;
            case CHANGE_BOARD_NAME:
                while (whiteboardClient.whiteboardClientGUI == null) {
                    try {
                        synchronized (actionQueueActionLock) {
                            actionQueueActionLock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                whiteboardClient.whiteboardClientGUI.mainFrame.setTitle(networkPackage.boardName, true);
                break;
            case SET_QUEUE:
                while (actionQueue == null) {
                    try {
                        synchronized (actionQueueActionLock) {
                            actionQueueActionLock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                actionQueue.setRealQueue(networkPackage.realQueue);
                break;
            case NEW_BOARD:
                JFrameNetwork oldFrame = whiteboardClient.whiteboardClientGUI.mainFrame;
                ChatRoom oldChatRoom = whiteboardClient.whiteboardClientGUI.chatRoom;
                new WhiteboardClient(this, oldFrame.getX(), oldFrame.getY());
                oldFrame.dispose();
                oldChatRoom.dispose();
                break;
            case CHAT:
                whiteboardClient.whiteboardClientGUI.chatRoom.addChat(networkPackage.user, networkPackage.chatMessage);
                break;
            case CHAT_HISTORY:
                ChatHistory chatHistory = networkPackage.chatHistory;
                User user;
                String chatMessage;
                while (whiteboardClient == null) {
                    try {
                        synchronized (whiteboardClientActionLock) {
                            whiteboardClientActionLock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ChatRoom chatRoom = whiteboardClient.whiteboardClientGUI.chatRoom;
                chatRoom.clearChat();
                for (int i = 0; i < chatHistory.getNumOfChat(); i++) {
                    user = chatHistory.getChatUser(i);
                    chatMessage = chatHistory.getChatMessage(i);
                    chatRoom.addChatNoScroll(user, chatMessage);
                }
                break;
            case CLOSE_ROOM:
                whiteboardClient.whiteboardClientGUI.mntmExit.doClick();
                break;
            default:
                System.out.println("Unexpected action type: " + actionType.name());
                break;
        }
        this.startReading();
    }

    public void setActionQueue(ActionQueue actionQueue) {
        this.actionQueue = actionQueue;
        synchronized (actionQueueActionLock) {
            actionQueueActionLock.notifyAll();
        }
    }

    public void setWhiteboardClient(WhiteboardClient whiteboardClient) {
        this.whiteboardClient = whiteboardClient;
        synchronized (whiteboardClientActionLock) {
            whiteboardClientActionLock.notifyAll();
        }
    }
}
