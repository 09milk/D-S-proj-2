package Client.MouseListeners;

import Client.DrawActions.PencilDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PencilListener extends AbstractToolButtonListener {

    private int oldX;
    private int oldY;

    public PencilListener(DrawingPanel drawingPanel){
        super(drawingPanel);
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new PencilDrawingListener();
    }


    private class PencilDrawingListener extends MouseInputAdapter{

        @Override
        public void mousePressed(MouseEvent event){
            oldX = event.getX();
            oldY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event){
            int x = event.getX();
            int y = event.getY();

            drawingPanel.drawActions.add(new PencilDraw(drawingPanel.color, oldX, oldY, x, y, drawingPanel.thickness));
            drawingPanel.repaint();

            oldX = x;
            oldY = y;
        }

        @Override
        public void mouseEntered(MouseEvent event){
            drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }
}
