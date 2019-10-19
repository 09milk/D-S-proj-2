package Client.DrawActions;

import java.awt.*;

public class RectangleDraw extends AbstractShapeDraw {

    public RectangleDraw(Color color, int upperLeftX, int upperLeftY, int width, int height, int size, boolean isFill) {
        super(color, upperLeftX, upperLeftY, width, height, size, isFill);
    }

    @Override
    protected void fillShape(Graphics2D graphics2D, int upperLeftX, int upperLeftY, int width, int height) {
        graphics2D.fillRect(upperLeftX, upperLeftY, width, height);
    }

    @Override
    protected void drawShape(Graphics2D graphics2D, int upperLeftX, int upperLeftY, int width, int height) {
        graphics2D.drawRect(upperLeftX, upperLeftY, width, height);
    }
}
