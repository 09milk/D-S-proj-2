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
        super.setIOStream(socket);

        startSending(new NetworkPackage(this.boardName));

        super.startReading();
    }

    @Override
    public void receivedPackage(NetworkPackage networkPackage) {
        ActionType actionType = networkPackage.actionType;
        switch (actionType) {
            case DRAW:
                actionQueue.addRealAction(networkPackage.drawAction);
                break;
            case REDO:
            default:
                break;
        }
        super.startReading();
    }


    public void setActionQueue(ActionQueue actionQueue) {
        this.actionQueue = actionQueue;
    }
}
