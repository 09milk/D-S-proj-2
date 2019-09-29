package Client;

import Network.UserName;

public class ClientMain {
    public static void main(String[] args) {
        startWhiteboard();
    }

    public static void startWhiteboard() {
        ClientNetworkController clientNetworkController = new ClientNetworkController(
                ClientConfig.SERVER_ADDRESS,
                ClientConfig.SERVER_PORT,
                ClientConfig.DEFAULT_ROOM_NAME,
                new UserName(ClientConfig.DEFAULT_USERNAME));
        try {
            clientNetworkController.startCommunication();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        new WhiteboardClient(clientNetworkController);
    }
}
