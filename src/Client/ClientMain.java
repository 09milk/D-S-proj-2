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
            popErrorBox(e.getMessage());
            System.exit(1);
        }
        new WhiteboardClient(clientNetworkController);
    }

    public static void loadConfig() {
        try {
            ClientConfig.loadConfig();
        } catch (Exception e) {
            popErrorBox(ClientConfig.CLIENT_CONFIG_ERROR_MSG);
        }
    }

    public static void popErrorBox(String errorMessage) {
        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                ClientConfig.ERROR_BOX_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
    }
}
