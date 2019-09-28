package Server;

import Network.ActionType;
import Network.NetworkController;
import Network.NetworkPackage;

import java.net.Socket;

public class ServerNetworkController extends NetworkController {

    private WhiteboardManager whiteboardManager;
    private RequestHandler requestHandler;

    public ServerNetworkController(RequestHandler requestHandler) {
        this.whiteboardManager = WhiteboardManager.getInstance();
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
        System.out.println(networkPackage.actionType.toString());
        ActionType actionType = networkPackage.actionType;
        switch (actionType) {
            case CONNECT:
                requestHandler.linkWhiteboard(networkPackage.boardName);
                break;
            case DRAW:
                requestHandler.addDrawAction(networkPackage.drawAction);
                break;
            case DISCONNECT:
                requestHandler.unlinkWhiteboard();
                break;
            default:
                break;
        }
        this.startReading();
    }

    public void sendPackage(int amountOfMembers) {
        NetworkPackage networkPackage = new NetworkPackage(amountOfMembers);
        startSending(networkPackage);
    }
}
