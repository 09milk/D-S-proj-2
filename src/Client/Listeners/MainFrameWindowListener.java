package Client.Listeners;

import Client.ClientNetworkController;
import Network.ActionType;
import Network.NetworkPackage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameWindowListener extends WindowAdapter {

    public ClientNetworkController clientNetworkController;

    public MainFrameWindowListener(ClientNetworkController clientNetworkController) {
        this.clientNetworkController = clientNetworkController;
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        try {
            clientNetworkController.sendPackage(new NetworkPackage(ActionType.DISCONNECT)).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
