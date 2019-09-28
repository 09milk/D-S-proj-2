package Client.Listeners.MenuBar.File;

import Client.ClientConstants;
import Client.DrawActions.IDrawAction;
import Client.DrawingPanel;

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
    private JFrame mainFrame;
    private DrawingPanel drawingPanel;
    private JFileChooser jFileChooser = new JFileChooser(new File(Paths.get("").toAbsolutePath().toString()));

    public OpenListener(JFrame mainFrame, DrawingPanel drawingPanel) {
        this.mainFrame = mainFrame;
        this.drawingPanel = drawingPanel;
        jFileChooser.setAcceptAllFileFilterUsed(false);
        addChoosableFileFilter();
    }

    private void addChoosableFileFilter() {
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(ClientConstants.CUSTOM_EXTENSION_DESCRIPTION,
                ClientConstants.CUSTOM_EXTENSION));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int result = jFileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            loadFile();
        }
    }

    private void loadFile() {
        File selectedFile = jFileChooser.getSelectedFile();
        String extension = ((FileNameExtensionFilter) jFileChooser.getFileFilter()).getExtensions()[0];
        drawingPanel.currentEditingFilename = selectedFile.getName();
        if (extension.equals(ClientConstants.CUSTOM_EXTENSION)) {
            loadCustomFile(selectedFile);
        }
    }

    private void loadCustomFile(File selectedFile) {
        ObjectInputStream objectInputStream;
        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
            objectInputStream = new ObjectInputStream(fileInputStream);
            mainFrame.setTitle(selectedFile.getName());
            drawingPanel.drawActions = (ArrayList<IDrawAction>) objectInputStream.readObject();
            drawingPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to Load.");
        }
    }
}
