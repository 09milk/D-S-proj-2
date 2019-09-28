package Client.Listeners.MenuBar.File;

import Client.DrawingPanel;
import Client.WhiteboardClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class NewListener implements ActionListener {

    private JFrame oldMainFrame;

    public NewListener(JFrame oldMainFrame) {
        this.oldMainFrame = oldMainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new WhiteboardClient();
        oldMainFrame.dispose();
    }
}
