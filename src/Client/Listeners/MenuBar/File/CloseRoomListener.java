package Client.Listeners.MenuBar.File;

import Client.ClientNetworkController;
import Network.ActionType;
import Network.NetworkPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseRoomListener extends ExitListener {

    public CloseRoomListener(JFrame mainFrame, ClientNetworkController clientNetworkController) {
        super(mainFrame, clientNetworkController);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            clientNetworkController.sendPackage(new NetworkPackage(ActionType.CLOSE_ROOM)).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.actionPerformed(event);
    }
}
