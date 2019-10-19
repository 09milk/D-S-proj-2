package Client.Listeners.ToolButton;

import Client.DrawActions.IDrawAction;
import Client.DrawActions.LineDraw;
import Client.DrawActions.OvalDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

abstract public class AbstractFreeDrawListener extends AbstractToolButtonListener {

    protected int oldX;
    protected int oldY;

    public AbstractFreeDrawListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new FreeDrawingListener();
    }

    @Override
    public void setCursor() {
        Toolkit toolkit = drawingPanel.getToolkit();
        drawingPanel.setCursor(toolkit.createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
    }

    abstract protected Color getColor();

    protected IDrawAction mouseMovingDrawAction(MouseEvent event, Color color) {
        int drawSize = drawingPanel.drawSize;
        int x = event.getX() - drawSize / 2;
        int y = event.getY() - drawSize / 2;
        return new OvalDraw(color, x, y, drawSize, drawSize, 0, true);
    }

    protected class FreeDrawingListener extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            oldX = event.getX();
            oldY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            this.mouseMoved(event);
            int x = event.getX();
            int y = event.getY();

            drawingPanel.drawActions.add(new LineDraw(getColor(), oldX, oldY, x, y, drawingPanel.drawSize));
            drawingPanel.repaint();

            oldX = x;
            oldY = y;
        }

        @Override
        public void mouseExited(MouseEvent event) {
            drawingPanel.resetTmpDrawAction();
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            drawingPanel.setTmpDrawAction(mouseMovingDrawAction(event, getColor()));
        }
    }
}
