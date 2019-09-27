package Client.MouseListeners.ToolButton;

import Client.DrawingPanel;
import Client.WhiteboardClient;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractToolButtonListener extends MouseInputAdapter {
    protected DrawingPanel drawingPanel;

    public AbstractToolButtonListener(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        drawingPanel.clearDrawingListener();
        drawingPanel.toolChanged();

        drawingPanel.addMouseListener(getDrawingListener());
        drawingPanel.addMouseMotionListener(getDrawingListener());
    }


    abstract protected MouseInputAdapter getDrawingListener();

    public void toolChanged(){};
}