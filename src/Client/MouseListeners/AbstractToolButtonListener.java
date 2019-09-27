package Client.MouseListeners;

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
    public void mouseReleased(MouseEvent event) {
        WhiteboardClient.clearDrawingListener(this.drawingPanel);

        drawingPanel.addMouseListener(getDrawingListener());
        drawingPanel.addMouseMotionListener(getDrawingListener());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        drawingPanel.setCursor(Cursor.getDefaultCursor());
    }

    abstract protected MouseInputAdapter getDrawingListener();

    //abstract public void draw(Object drawAction);
}