package Client.Listeners.MenuBar.Privilege;

import Client.ClientNetworkController;
import Network.ActionType;
import Network.NetworkPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BecomeManagerListener implements ActionListener {
    private ClientNetworkController clientNetworkController;

    public BecomeManagerListener(ClientNetworkController clientNetworkController) {
        this.clientNetworkController = clientNetworkController;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.SET_MANAGER, clientNetworkController.user));
    }
}
