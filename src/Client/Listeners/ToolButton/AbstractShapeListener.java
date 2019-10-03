package Client.Listeners.ToolButton;

import Client.DrawActions.AbstractShapeDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

abstract public class AbstractShapeListener extends AbstractToolButtonListener {

    private int originalX;
    private int originalY;

    private AbstractShapeDraw tmpDrawAction;

    public AbstractShapeListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new ShapeDrawingListener();
    }

    abstract protected AbstractShapeDraw getAbstractShapeFill(int upperLeftX, int upperLeftY, int width, int height);

    @Override
    public void setCursor(){
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    protected class ShapeDrawingListener extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            originalX = event.getX();
            originalY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int currentX;
            int currentY;
            if (drawingPanel.isInPerfectDraw) {
                Point closestPoint = getClosestPointOnPerfectLine(originalX, originalY, event.getX(), event.getY());
                currentX = closestPoint.x;
                currentY = closestPoint.y;
            } else {
                currentX = event.getX();
                currentY = event.getY();
            }
            int width = Math.abs(currentX - originalX);
            int height = Math.abs(currentY - originalY);
            int upperLeftX = Math.min(currentX, originalX);
            int upperLeftY = Math.min(currentY, originalY);

            tmpDrawAction = getAbstractShapeFill(upperLeftX, upperLeftY, width, height);
            drawingPanel.setTmpDrawAction(tmpDrawAction);

            drawingPanel.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            drawingPanel.resetTmpDrawAction();
            drawingPanel.drawActions.add(tmpDrawAction);
            drawingPanel.repaint();
        }

        private Point getClosestPointOnPerfectLine(int originalX, int originalY, int pointX, int pointY) {
            int x1, y1, x2, y2, x3, y3, x4, y4;

            x1 = originalX;
            y1 = originalY;
            x2 = x1 + 1;
            y2 = y1 + 1;

            x3 = pointX;
            y3 = pointY;
            x4 = x3 + 1;
            y4 = y3 - 1;
            Point closestPoint1 = getClosestPointOnLine(x1, y1, x2, y2, x3, y3, x4, y4);

            x2 = x1 - 1;
            y4 = y3 + 1;
            Point closestPoint2 = getClosestPointOnLine(x1, y1, x2, y2, x3, y3, x4, y4);

            return getClosestPoint(closestPoint1, closestPoint2, new Point(pointX, pointY));
        }

        private Point getClosestPointOnLine(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
            float tmp = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
            float pX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / tmp;
            float pY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / tmp;

            return new Point(Math.round(pX), Math.round(pY));
        }

        private Point getClosestPoint(Point p1, Point p2, Point comparePoint) {
            int d1 = distanceSquare(p1, comparePoint);
            int d2 = distanceSquare(p2, comparePoint);
            if (d1 > d2) {
                return p2;
            } else {
                return p1;
            }
        }

        private int distanceSquare(Point p1, Point p2) {
            int dx = p1.x - p2.x;
            int dy = p1.y - p2.y;
            return dx * dx + dy * dy;
        }
    }
}
