package Client.Listeners.MenuBar.File;

import Client.ClientNetworkController;
import Client.Listeners.mainFrameWindowListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitListener implements ActionListener {

    protected JFrame mainFrame;
    protected ClientNetworkController clientNetworkController;

    public ExitListener(JFrame mainFrame, ClientNetworkController clientNetworkController) {
        this.mainFrame = mainFrame;
        this.clientNetworkController = clientNetworkController;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new mainFrameWindowListener(clientNetworkController).windowClosing(null);
        mainFrame.dispose();
        System.exit(0);
    }
}
