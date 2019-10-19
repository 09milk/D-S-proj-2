package Client;

import java.io.IOException;
import java.net.Socket;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;
import Network.User;
import Server.ChatHistory;

public class ClientNetworkController extends NetworkController {

    public final Object actionLock = new Object();
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

        startSending(new NetworkPackage(ActionType.CONNECT, user, this.roomName));

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
            case MEMBER_AMOUNT:
                int amount = networkPackage.amountOfMembers;
                String text = String.format(ClientConfig.CURRENT_MEMBER_STRING, amount);
                try {
                    whiteboardClient.whiteboardClientGUI.btnCurrentMember.setText(text);
                } catch (NullPointerException ignored) {
                }
                break;
            case CHANGE_BOARD_NAME:
                while (whiteboardClient.whiteboardClientGUI == null) {
                    try {
                        synchronized (actionLock) {
                            actionLock.wait();
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
                        synchronized (actionLock) {
                            actionLock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                actionQueue.setRealQueue(networkPackage.realQueue);
                break;
            case NEW_BOARD:
                JFrameNetwork oldFrame = whiteboardClient.whiteboardClientGUI.mainFrame;
                new WhiteboardClient(this, oldFrame.getX(), oldFrame.getY());
                oldFrame.dispose();
                break;
            case CHAT:
                whiteboardClient.whiteboardClientGUI.chatRoom.addChat(networkPackage.user, networkPackage.chatMessage);
                break;
            case CHAT_HISTORY:
                ChatHistory chatHistory = networkPackage.chatHistory;
                User user;
                String chatMessage;
                for (int i = 0; i < chatHistory.getNumOfChat(); i++){
                    user = chatHistory.getChatUser(i);
                    chatMessage = chatHistory.getChatMessage(i);
                    whiteboardClient.whiteboardClientGUI.chatRoom.addChat(user, chatMessage);
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

    public void addChat(String message){
        startSending(new NetworkPackage(ActionType.CHAT, user, message));
    }

    public void setActionQueue(ActionQueue actionQueue) {
        this.actionQueue = actionQueue;
        synchronized (actionLock) {
            actionLock.notifyAll();
        }
    }

    public void setWhiteboardClient(WhiteboardClient whiteboardClient) {
        this.whiteboardClient = whiteboardClient;
    }
}
