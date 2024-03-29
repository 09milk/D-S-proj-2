package Client.Listeners.MenuBar.File;

import Client.ChatRoom;
import Client.ClientNetworkController;
import Client.WhiteboardClient;
import Network.ActionType;
import Network.NetworkPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewListener implements ActionListener {

    private JFrame oldMainFrame;
    private ChatRoom chatRoom;
    private ClientNetworkController clientNetworkController;

    public NewListener(JFrame oldMainFrame, ChatRoom chatRoom, ClientNetworkController clientNetworkController) {
        this.oldMainFrame = oldMainFrame;
        this.chatRoom = chatRoom;
        this.clientNetworkController = clientNetworkController;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.NEW_BOARD));
        new WhiteboardClient(clientNetworkController, oldMainFrame.getX(), oldMainFrame.getY());
        oldMainFrame.dispose();
        chatRoom.dispose();
    }
}
