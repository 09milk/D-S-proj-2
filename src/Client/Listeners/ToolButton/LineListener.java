package Client.Listeners.ToolButton;

import Client.DrawActions.LineDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class LineListener extends AbstractToolButtonListener {

    private int originalX;
    private int originalY;

    private LineDraw tmpDrawAction;

    public LineListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new LineDrawingListener();
    }

    @Override
    public void setCursor() {
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    protected class LineDrawingListener extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            originalX = event.getX();
            originalY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int currentX = event.getX();
            int currentY = event.getY();

            tmpDrawAction = new LineDraw(drawingPanel.color, originalX, originalY, currentX, currentY, drawingPanel.size);
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

