package Client;

import Network.User;

import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        loadConfig();
        startWhiteboard();
    }

    public static void startWhiteboard() {
        ClientNetworkController clientNetworkController = new ClientNetworkController(
                ClientConfig.SERVER_ADDRESS,
                ClientConfig.SERVER_PORT,
                ClientConfig.DEFAULT_ROOM_NAME,
                new User(ClientConfig.DEFAULT_USERNAME));
        try {
            clientNetworkController.startCommunication();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        new WhiteboardClient(clientNetworkController);
    }

    public static void loadConfig() {
        try {
            ClientConfig.loadConfig();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    ClientConfig.CLIENT_CONFIG_ERROR_MSG,
                    ClientConfig.ERROR_BOX_TITLE,
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(1);
        }
    }
}
