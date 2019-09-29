package Client.Listeners.MenuBar.File;

import Client.JFrameNetwork;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitListener implements ActionListener {

    private JFrame mainFrame;

    public ExitListener(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        mainFrame.dispose();
        System.exit(0);
    }
}
