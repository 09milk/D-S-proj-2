package Client.Listeners.MenuBar.File;

import Client.ClientNetworkController;
import Client.WhiteboardClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewListener implements ActionListener {

    private JFrame oldMainFrame;
    private ClientNetworkController clientNetworkController;

    public NewListener(JFrame oldMainFrame, ClientNetworkController clientNetworkController) {
        this.oldMainFrame = oldMainFrame;
        this.clientNetworkController = clientNetworkController;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new WhiteboardClient(clientNetworkController);
        oldMainFrame.dispose();
    }
}
