package Client.Listeners.ToolButton;

import Client.DrawActions.PencilDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class EraserListener extends AbstractToolButtonListener {

    private int oldX;
    private int oldY;

    public EraserListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new EraserDrawingListener();
    }

    @Override
    public void setCursor(){
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    private class EraserDrawingListener extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            oldX = event.getX();
            oldY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();

            drawingPanel.drawActions.add(new PencilDraw(drawingPanel.getBackground(), oldX, oldY, x, y, drawingPanel.size));
            drawingPanel.repaint();

            oldX = x;
            oldY = y;
        }
    }
}
