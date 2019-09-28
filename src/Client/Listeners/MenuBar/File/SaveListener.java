package Client.Listeners.MenuBar.File;

import Client.DrawingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveListener implements ActionListener {
    private JFrame mainFrame;
    private DrawingPanel drawingPanel;

    public SaveListener(JFrame mainFrame, DrawingPanel drawingPanel) {
        this.mainFrame = mainFrame;
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(drawingPanel.currentEditingFilename != null) {
            saveFile();
        } else{
            (new SaveAsListener(mainFrame, drawingPanel, true)).actionPerformed(null);
        }
    }

    private void saveFile() {
        (new SaveAsListener(mainFrame, drawingPanel)).saveCustomFileWithName(drawingPanel.currentEditingFilename);
    }
}
