package Client.Listeners.ToolButton;

import Client.DrawActions.AbstractShapeDraw;
import Client.DrawActions.RectangleDraw;
import Client.DrawingPanel;

public class RectangleListener extends AbstractShapeListener {

    public RectangleListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected AbstractShapeDraw getAbstractShapeFill(int upperLeftX, int upperLeftY, int width, int height) {
        return new RectangleDraw(drawingPanel.color, upperLeftX, upperLeftY, width, height, drawingPanel.size, true);
    }
}
