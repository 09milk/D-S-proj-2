package Client.MouseListeners.MenuBar;

import Client.DrawingPanel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ColorSelectionListener extends MouseInputAdapter {
    private DrawingPanel drawingPanel;

    public ColorSelectionListener(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseClicked(MouseEvent event){
        drawingPanel.color = JColorChooser.showDialog(null, "Select color", drawingPanel.color);
    }
}
