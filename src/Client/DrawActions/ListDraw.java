package Client.DrawActions;

import java.awt.*;
import java.util.ArrayList;

public class ListDraw implements IDrawAction {

    public ArrayList<IDrawAction> drawActions = new ArrayList<>();

    @Override
    public void draw(Graphics2D graphics) {
        for (IDrawAction drawAction : drawActions) {
            drawAction.draw(graphics);
        }
    }
}
