package Client.DrawActions;

import java.awt.*;

public class OvalDraw extends AbstractShapeDraw {

    public OvalDraw(Color color, int upperLeftX, int upperLeftY, int width, int height, int size, boolean isFill) {
        super(color, upperLeftX, upperLeftY, width, height, size, isFill);
    }

    @Override
    protected void fillShape(Graphics2D graphics2D, int upperLeftX, int upperLeftY, int width, int height) {
        graphics2D.fillOval(upperLeftX, upperLeftY, width, height);
    }

    @Override
    protected void drawShape(Graphics2D graphics2D, int upperLeftX, int upperLeftY, int width, int height) {
        graphics2D.drawOval(upperLeftX, upperLeftY, width, height);
    }
}
