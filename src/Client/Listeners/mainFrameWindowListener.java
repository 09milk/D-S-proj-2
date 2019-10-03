package Client.Listeners;

import Client.ClientNetworkController;
import Network.ActionType;
import Network.NetworkPackage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class mainFrameWindowListener extends WindowAdapter {

    public ClientNetworkController clientNetworkController;

    public mainFrameWindowListener(ClientNetworkController clientNetworkController) {
        this.clientNetworkController = clientNetworkController;
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.DISCONNECT));
        super.windowClosing(windowEvent);
    }
}