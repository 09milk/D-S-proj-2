package Client;


import Client.DrawActions.IDrawAction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {

    public Color color = Color.BLACK;
    public int thickness = 10;
    //TODO: change Object to DrawAction
    public ArrayList<IDrawAction> drawActions = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        for(IDrawAction drawAction : drawActions){
            this.draw((Graphics2D)graphics, drawAction);
        }
    }

    private void draw(Graphics2D graphics, IDrawAction drawAction) {
        drawAction.draw(graphics);
    }

}
