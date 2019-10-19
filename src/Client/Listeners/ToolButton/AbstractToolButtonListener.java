package Client.Listeners.ToolButton;

import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractToolButtonListener implements ActionListener {
    protected DrawingPanel drawingPanel;

    public AbstractToolButtonListener(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        drawingPanel.clearDrawingListener();

        setCursor();
        drawingPanel.addMouseListener(getDrawingListener());
        drawingPanel.addMouseMotionListener(getDrawingListener());
    }


    abstract protected MouseInputAdapter getDrawingListener();

    abstract public void setCursor();
}