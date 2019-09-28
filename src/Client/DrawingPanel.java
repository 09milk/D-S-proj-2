package Client;


import Client.DrawActions.IDrawAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingPanel extends JPanel {

    public Color color = Color.BLACK;
    public int size = ClientConstants.DEFAULT_SIZE;
    public ActionQueue drawActions = new ActionQueue();
    private IDrawAction tmpDrawAction;
    public String currentEditingFilename = null;

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (IDrawAction drawAction : drawActions) {
            this.draw((Graphics2D) graphics, drawAction);
        }

        try {
            this.draw((Graphics2D) graphics, tmpDrawAction);
        } catch (NullPointerException ignored) {
        }
    }

    private void draw(Graphics2D graphics, IDrawAction drawAction) {
        drawAction.draw(graphics);
    }

    public void clearDrawingListener() {
        for (MouseListener listener : this.getMouseListeners()) {
            this.removeMouseListener(listener);
        }
        for (MouseMotionListener listener : this.getMouseMotionListeners()) {
            this.removeMouseMotionListener(listener);
        }
    }

    public void setTmpDrawAction(IDrawAction tmpDrawAction){
        this.tmpDrawAction = tmpDrawAction;
    }

    public void resetTmpDrawAction(){
        setTmpDrawAction(null);
    }

}
