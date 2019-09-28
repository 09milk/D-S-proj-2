package Client.Listeners.MenuBar.Style;

import Client.DrawingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorSelectionListener implements ActionListener {
    private DrawingPanel drawingPanel;

    public ColorSelectionListener(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        drawingPanel.color = JColorChooser.showDialog(null, "Select color", drawingPanel.color);
    }
}
