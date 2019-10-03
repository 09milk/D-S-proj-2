package Client.Listeners.ToolButton;

import Client.DrawActions.OvalDraw;
import Client.DrawActions.RectangleDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class RectangleListener extends AbstractToolButtonListener {

    private int originalX;
    private int originalY;

    private RectangleDraw tmpDrawAction;

    public RectangleListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new OvalDrawingListener();
    }


    protected class OvalDrawingListener extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            originalX = event.getX();
            originalY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int currentX = event.getX();
            int currentY = event.getY();
            int width = Math.abs(currentX - originalX);
            int height = Math.abs(currentY - originalY);
            int upperLeftX = Math.min(currentX, originalX);
            int upperLeftY = Math.min(currentY, originalY);

            tmpDrawAction = new RectangleDraw(drawingPanel.color, upperLeftX, upperLeftY, width, height, drawingPanel.size, true);
            drawingPanel.setTmpDrawAction(tmpDrawAction);

            drawingPanel.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            drawingPanel.resetTmpDrawAction();
            drawingPanel.drawActions.add(tmpDrawAction);
            drawingPanel.repaint();
        }
    }
}
