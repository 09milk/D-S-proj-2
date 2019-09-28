package Client;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkController extends NetworkController {

    private String address;
    private int port;

    private ActionQueue actionQueue;
    private WhiteboardClient whiteboardClient;


    public ClientNetworkController(String address, int port, String boardName) {
        this.address = address;
        this.port = port;
        this.boardName = boardName;
    }

    public void startCommunication() throws Exception {
        Socket socket;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            throw new Exception("Failed to start socket");
        }
        this.setIOStream(socket);

        startSending(new NetworkPackage(ActionType.CONNECT, this.boardName));

        this.startReading();
    }

    @Override
    public void receivedPackage(NetworkPackage networkPackage) {
        ActionType actionType = networkPackage.actionType;
        switch (actionType) {
            case DRAW:
                actionQueue.addRealAction(networkPackage.drawAction);
                break;
            case MEMBER_AMOUNT:
                int amount = networkPackage.amountOfMembers;
                String text = String.format(ClientConstants.CURRENT_MEMBER_STRING, amount);
                try {
                    whiteboardClient.whiteboardClientGUI.btnCurrentMember.setText(text);
                }catch(NullPointerException ignored){}
                break;
            case REDO:
            default:
                break;
        }
        this.startReading();
    }


    public void setActionQueue(ActionQueue actionQueue) {
        this.actionQueue = actionQueue;
    }

    public void setWhiteboardClient(WhiteboardClient whiteboardClient) {
        this.whiteboardClient = whiteboardClient;
    }
}
