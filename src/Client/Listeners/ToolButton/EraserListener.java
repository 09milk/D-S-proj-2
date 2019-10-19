package Client.Listeners.ToolButton;

import Client.DrawActions.IDrawAction;
import Client.DrawActions.ListDraw;
import Client.DrawActions.OvalDraw;
import Client.DrawingPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EraserListener extends AbstractFreeDrawListener {

    public EraserListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    private static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000.0;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }

    @Override
    protected Color getColor() {
        return drawingPanel.getBackground();
    }

    @Override
    protected IDrawAction mouseMovingDrawAction(MouseEvent event, Color color) {
        int outerCircleSizeDifference = 2;
        int drawSize = drawingPanel.drawSize + outerCircleSizeDifference;
        int x = event.getX() - drawSize / 2;
        int y = event.getY() - drawSize / 2;
        ListDraw listDraw = new ListDraw();
        listDraw.drawActions.add(new OvalDraw(getContrastColor(color), x, y, drawSize, drawSize, 0, true));
        listDraw.drawActions.add(super.mouseMovingDrawAction(event, color));
        return listDraw;
    }
}
