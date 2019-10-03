package Client.DrawActions;

import java.awt.*;
import java.io.Serializable;

public class PencilDraw implements IDrawAction {

    public Color color;
    public int oldX;
    public int oldY;
    public int x;
    public int y;
    public int size;

    public PencilDraw(Color color, int oldX, int oldY, int x, int y, int size) {
        this.color = color;
        this.oldX = oldX;
        this.oldY = oldY;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.drawLine(x, y, oldX, oldY);
    }
}
