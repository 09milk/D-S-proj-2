package Client.DrawActions;

import java.awt.*;

public class TextDraw implements IDrawAction {

    public Color color;
    public int x;
    public int y;
    public int size;
    public String string;

    public TextDraw(Color color, int x, int y, int size, String string) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;
        this.string = string;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setFont(graphics.getFont().deriveFont((float) size));
        graphics.drawString(string, x, y);
    }
}
