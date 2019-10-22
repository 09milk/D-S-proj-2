package Client.Listeners.MenuBar.Privilege;

import Client.ClientNetworkController;
import Network.ActionType;
import Network.NetworkPackage;
import Network.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KickUserListener implements ActionListener {
    private ClientNetworkController clientNetworkController;
    private User targetUser;

    public KickUserListener(ClientNetworkController clientNetworkController, User targetUser) {
        this.clientNetworkController = clientNetworkController;
        this.targetUser = targetUser;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.KICK_USER, targetUser));
    }
}
