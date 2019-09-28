package Client.Listeners.MenuBar.File;

import Client.ClientConstants;
import Client.DrawActions.IDrawAction;
import Client.DrawingPanel;
import com.sun.security.ntlm.Client;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveAsListener implements ActionListener {
    private JFrame mainFrame;
    private DrawingPanel drawingPanel;
    private JFileChooser jFileChooser = new JFileChooser(new File(Paths.get("").toAbsolutePath().toString()));

    public SaveAsListener(JFrame mainFrame, DrawingPanel drawingPanel) {
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
    public void actionPerformed(ActionEvent e) {
        int result = jFileChooser.showSaveDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION){
            saveAsFile();
        }
    }

    private void saveAsFile() {
        File selectedFile = jFileChooser.getSelectedFile();
        String extension = ((FileNameExtensionFilter)jFileChooser.getFileFilter()).getExtensions()[0];
        if(extension.equals(ClientConstants.CUSTOM_EXTENSION)){
            saveAsCustomFile(selectedFile);
        }

    }

    private void saveAsCustomFile(File selectedFile) {
        Pattern regexPattern = Pattern.compile("^.*\\.(" + ClientConstants.CUSTOM_EXTENSION + ")$");
        String filename = selectedFile.getName();
        if(!regexPattern.matcher(filename).find()){
            filename = filename + "." + ClientConstants.CUSTOM_EXTENSION;
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(drawingPanel.drawActions);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to Save.");
        }
    }
}
