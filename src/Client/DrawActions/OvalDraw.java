package Client.DrawActions;

import java.awt.*;
import java.io.Serializable;

public class OvalDraw implements IDrawAction, Serializable {

    public Color color;
    public int upperLeftX;
    public int upperLeftY;
    public int width;
    public int height;
    public int size;
    public boolean isFill;

    public OvalDraw(Color color, int upperLeftX, int upperLeftY, int width, int height, int size, boolean isFill) {
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
        if(isFill) {
            graphics.fillOval(upperLeftX, upperLeftY, width, height);
        } else{
            graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND ,BasicStroke.JOIN_ROUND));
            graphics.drawOval(upperLeftX, upperLeftY, width, height);
        }
    }
}
