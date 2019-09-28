package Client;


import Client.DrawActions.IDrawAction;
import Client.Listeners.ToolButton.AbstractToolButtonListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {

    public Color color = Color.BLACK;
    public int size = ClientConstants.DEFAULT_SIZE;
    public ArrayList<IDrawAction> drawActions = new ArrayList<>();
    public IDrawAction tmpDrawAction;
    public ArrayDeque<AbstractToolButtonListener> toolChangeObservers = new ArrayDeque<>();

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        for(IDrawAction drawAction : drawActions){
            this.draw((Graphics2D)graphics, drawAction);
        }

        try {
            this.draw((Graphics2D) graphics, tmpDrawAction);
        } catch(NullPointerException ignored){}
    }

    private void draw(Graphics2D graphics, IDrawAction drawAction) {
        drawAction.draw(graphics);
    }

    public void clearDrawingListener() {
        for(MouseListener listener : this.getMouseListeners()){
            this.removeMouseListener(listener);
        }
        for(MouseMotionListener listener : this.getMouseMotionListeners()){
            this.removeMouseMotionListener(listener);
        }
    }

}
