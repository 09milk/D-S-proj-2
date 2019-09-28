package Client;

public class ClientMain {
    public static void main(String[] args) {
        startWhiteboard();
    }

    public static void startWhiteboard() {
        ClientNetworkController clientNetworkController = new ClientNetworkController(
                ClientConstants.SERVER_ADDRESS,
                ClientConstants.SERVER_PORT,
                ClientConstants.DEFAULT_BOARD_NAME);
        try {
            clientNetworkController.startCommunication();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        new WhiteboardClient(clientNetworkController);
    }
}
