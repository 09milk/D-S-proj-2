package Client.Listeners;

import Client.DrawingPanel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class SliderListener extends MouseInputAdapter {
    private DrawingPanel drawingPanel;

    public SliderListener(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseReleased(MouseEvent event){
        drawingPanel.size = ((JSlider) event.getSource()).getValue();
    }
}
