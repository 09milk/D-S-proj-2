package Client.DrawActions;

import java.awt.*;

abstract public class AbstractShapeDraw implements IDrawAction {

    public Color color;
    public int upperLeftX;
    public int upperLeftY;
    public int width;
    public int height;
    public int size;
    public boolean isFill;

    public AbstractShapeDraw(Color color, int upperLeftX, int upperLeftY, int width, int height, int size, boolean isFill) {
        this.color = color;
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.width = width;
        this.height = height;
        this.size = size;
        this.isFill = isFill;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        if (isFill) {
            fillShape(graphics, upperLeftX, upperLeftY, width, height);
        } else {
            graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawShape(graphics, upperLeftX, upperLeftY, width, height);
        }
    }

    abstract protected void fillShape(Graphics2D graphics2D, int upperLeftX, int upperLeftY, int width, int height);

    abstract protected void drawShape(Graphics2D graphics2D, int upperLeftX, int upperLeftY, int width, int height);
}
