package Client;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;
import Network.UserName;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkController extends NetworkController {

    private String address;
    private int port;
    public UserName userName;

    private ActionQueue actionQueue;
    private final Object actionLock = new Object();
    private WhiteboardClient whiteboardClient;


    public ClientNetworkController(String address, int port, String roomName, UserName userName) {
        this.address = address;
        this.port = port;
        this.roomName = roomName;
        this.userName = userName;
    }

    public void startCommunication() throws Exception {
        Socket socket;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            throw new Exception("Failed to start socket");
        }
        this.setIOStream(socket);

        startSending(new NetworkPackage(ActionType.CONNECT, userName, this.roomName));

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
            case CHANGE_LOCAL_NAME:
                whiteboardClient.whiteboardClientGUI.mainFrame.setTitle(networkPackage.roomName, true);
                break;
            case SET_QUEUE:
                while (actionQueue == null){
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
                new WhiteboardClient(this, false, oldFrame.getX(), oldFrame.getY());
                oldFrame.dispose();
                break;
            default:
                System.out.println("Unexpected action type: " + actionType.name());
                break;
        }
        this.startReading();
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
