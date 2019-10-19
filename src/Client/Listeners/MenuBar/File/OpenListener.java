package Client.Listeners.MenuBar.File;

import Client.ClientConfig;
import Client.ClientNetworkController;
import Client.DrawActions.IDrawAction;
import Client.DrawingPanel;
import Client.WhiteboardClient;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OpenListener implements ActionListener {
    private JFrame oldMainFrame;
    private DrawingPanel drawingPanel;
    private ClientNetworkController clientNetworkController;
    private JFileChooser jFileChooser = new JFileChooser(new File(Paths.get("").toAbsolutePath().toString()));

    public OpenListener(JFrame oldMainFrame, DrawingPanel drawingPanel, ClientNetworkController clientNetworkController) {
        this.oldMainFrame = oldMainFrame;
        this.drawingPanel = drawingPanel;
        this.clientNetworkController = clientNetworkController;
        jFileChooser.setAcceptAllFileFilterUsed(false);
        addChoosableFileFilter();
    }

    private void addChoosableFileFilter() {
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(ClientConfig.CUSTOM_EXTENSION_DESCRIPTION,
                ClientConfig.CUSTOM_EXTENSION));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int result = jFileChooser.showOpenDialog(oldMainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            loadFile();
        }
    }

    private void loadFile() {
        File selectedFile = jFileChooser.getSelectedFile();
        String extension = ((FileNameExtensionFilter) jFileChooser.getFileFilter()).getExtensions()[0];
        drawingPanel.currentEditingFilename = selectedFile.getName();
        if (extension.equals(ClientConfig.CUSTOM_EXTENSION)) {
            loadCustomFile(selectedFile);
        }
    }

    private void loadCustomFile(File selectedFile) {
        ObjectInputStream objectInputStream;
        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
            objectInputStream = new ObjectInputStream(fileInputStream);
            String name = selectedFile.getName();
            ArrayList<IDrawAction> realQueue = (ArrayList<IDrawAction>) objectInputStream.readObject();
            new WhiteboardClient(clientNetworkController, name, realQueue, oldMainFrame.getX(), oldMainFrame.getY());
            oldMainFrame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to Load.");
        }
    }
}
