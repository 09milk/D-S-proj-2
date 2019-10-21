package Client.Listeners.MenuBar.Privilege;

import Client.ClientConfig;
import Client.ClientNetworkController;
import Client.WhiteboardClientGUI;
import Network.ActionType;
import Network.NetworkPackage;
import Network.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcceptUserListener implements ActionListener {
    private ClientNetworkController clientNetworkController;
    private User targetUser;
    private JMenuItem selectUserButton;
    private WhiteboardClientGUI whiteboardClientGUI;

    public AcceptUserListener(
            ClientNetworkController clientNetworkController,
            User targetUser,
            JMenuItem selectUserButton,
            WhiteboardClientGUI whiteboardClientGUI) {
        this.clientNetworkController = clientNetworkController;
        this.targetUser = targetUser;
        this.selectUserButton = selectUserButton;
        this.whiteboardClientGUI = whiteboardClientGUI;
        whiteboardClientGUI.btnChatRoom.setText("*" + ClientConfig.CHAT_ROOM_STRING + "*");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        whiteboardClientGUI.mnAcceptUser.remove(selectUserButton);
        if (whiteboardClientGUI.mnAcceptUser.getItemCount() == 0) {
            whiteboardClientGUI.btnChatRoom.setText(ClientConfig.CHAT_ROOM_STRING);
        }
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.ACCEPT_USER, targetUser));
    }
}
