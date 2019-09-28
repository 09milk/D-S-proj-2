package Client.Listeners.ToolButton;

import Client.DrawActions.PencilDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PencilListener extends AbstractToolButtonListener {

    private int oldX;
    private int oldY;

    public PencilListener(DrawingPanel drawingPanel){
        super(drawingPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        super.actionPerformed(event);
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return new PencilDrawingListener();
    }


    protected class PencilDrawingListener extends MouseInputAdapter{

        @Override
        public void mousePressed(MouseEvent event){
            oldX = event.getX();
            oldY = event.getY();
        }

        @Override
        public void mouseDragged(MouseEvent event){
            int x = event.getX();
            int y = event.getY();

            drawingPanel.drawActions.add(new PencilDraw(drawingPanel.color, oldX, oldY, x, y, drawingPanel.size));
            drawingPanel.repaint();

            oldX = x;
            oldY = y;
        }
    }
}
