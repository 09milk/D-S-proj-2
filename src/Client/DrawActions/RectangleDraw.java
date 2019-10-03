package Client.DrawActions;

import java.awt.*;

public class RectangleDraw implements IDrawAction {

    public Color color;
    public int upperLeftX;
    public int upperLeftY;
    public int width;
    public int height;
    public int size;
    public boolean isFill;

    public RectangleDraw(Color color, int upperLeftX, int upperLeftY, int width, int height, int size, boolean isFill) {
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
            graphics.fillRect(upperLeftX, upperLeftY, width, height);
        } else {
            graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.drawRect(upperLeftX, upperLeftY, width, height);
        }
    }
}
