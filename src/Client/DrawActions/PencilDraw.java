package Client.DrawActions;

import java.awt.*;
import java.io.Serializable;

public class PencilDraw implements IDrawAction, Serializable {

    public Color color;
    public int oldX;
    public int oldY;
    public int x;
    public int y;
    public int thickness;

    public PencilDraw(Color color, int oldX, int oldY, int x, int y, int thickness) {
        this.color = color;
        this.oldX = oldX;
        this.oldY = oldY;
        this.x = x;
        this.y = y;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND ,BasicStroke.JOIN_ROUND));
        graphics.drawLine(x, y, oldX, oldY);
    }
}
