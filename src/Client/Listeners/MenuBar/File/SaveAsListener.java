package Client.Listeners.MenuBar.File;

import Client.ClientConstants;
import Client.DrawingPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class SaveAsListener implements ActionListener {
    private JFrame mainFrame;
    private DrawingPanel drawingPanel;
    private JFileChooser jFileChooser = new JFileChooser(new File(Paths.get("").toAbsolutePath().toString()));

    public SaveAsListener(JFrame mainFrame, DrawingPanel drawingPanel) {
        this(mainFrame, drawingPanel, false);
    }

    public SaveAsListener(JFrame mainFrame, DrawingPanel drawingPanel, boolean customExtensionOnly) {
        this.mainFrame = mainFrame;
        this.drawingPanel = drawingPanel;
        jFileChooser.setAcceptAllFileFilterUsed(false);
        if (customExtensionOnly) {
            addCustomExtensionFileFilter();
        } else {
            addChoosableFileFilter();
        }
    }

    private void addChoosableFileFilter() {
        addCustomExtensionFileFilter();
    }

    private void addCustomExtensionFileFilter() {
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(ClientConstants.CUSTOM_EXTENSION_DESCRIPTION,
                ClientConstants.CUSTOM_EXTENSION));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int result = jFileChooser.showSaveDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        File selectedFile = jFileChooser.getSelectedFile();
        String extension = ((FileNameExtensionFilter) jFileChooser.getFileFilter()).getExtensions()[0];
        if (extension.equals(ClientConstants.CUSTOM_EXTENSION)) {
            saveAsCustomFile(selectedFile);
        }

    }

    private void saveAsCustomFile(File selectedFile) {
        Pattern regexPattern = Pattern.compile("^.*\\.(" + ClientConstants.CUSTOM_EXTENSION + ")$");
        String filename = selectedFile.getName();
        if (!regexPattern.matcher(filename).find()) {
            filename = filename + "." + ClientConstants.CUSTOM_EXTENSION;
        }
        saveCustomFileWithName(filename);
    }

    void saveCustomFileWithName(String filename) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(drawingPanel.drawActions.getRealQueue());
            mainFrame.setTitle(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to Save.");
        }
    }
}
