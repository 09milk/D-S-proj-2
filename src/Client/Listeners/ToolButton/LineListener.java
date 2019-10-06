package Client.Listeners.ToolButton;

import Client.DrawActions.LineDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
    public void setCursor(){
        Toolkit toolkit = drawingPanel.getToolkit();
        drawingPanel.setCursor(toolkit.createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null));
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

            tmpDrawAction = new LineDraw(drawingPanel.color, originalX, originalY, currentX, currentY, drawingPanel.drawSize);
            drawingPanel.setTmpDrawAction(tmpDrawAction);
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            drawingPanel.drawActions.add(tmpDrawAction);
            drawingPanel.resetTmpDrawAction();
        }

        @Override
        public void mouseExited(MouseEvent event){
            drawingPanel.resetTmpDrawAction();
        }

        @Override
        public void mouseMoved(MouseEvent event){
            int x = event.getX();
            int y = event.getY();

            drawingPanel.setTmpDrawAction(new LineDraw(drawingPanel.color, x, y, x, y, drawingPanel.drawSize));
        }
    }
}

