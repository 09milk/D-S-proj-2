package Client.DrawActions;

import java.awt.*;

public class LineDraw implements IDrawAction {

    public Color color;
    public int originalX;
    public int originalY;
    public int finalX;
    public int finalY;
    public int size;

    public LineDraw(Color color, int originalX, int originalY, int finalX, int finalY, int size) {
        this.color = color;
        this.originalX = originalX;
        this.originalY = originalY;
        this.finalX = finalX;
        this.finalY = finalY;
        this.size = size;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.drawLine(originalX, originalY, finalX, finalY);
    }
}

