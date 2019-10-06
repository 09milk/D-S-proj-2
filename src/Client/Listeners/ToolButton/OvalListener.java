package Client.Listeners.ToolButton;

import Client.DrawActions.AbstractShapeDraw;
import Client.DrawActions.OvalDraw;
import Client.DrawingPanel;

public class OvalListener extends AbstractShapeListener {

    public OvalListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected AbstractShapeDraw getAbstractShapeFill(int upperLeftX, int upperLeftY, int width, int height) {
        return new OvalDraw(drawingPanel.color, upperLeftX, upperLeftY, width, height, drawingPanel.drawSize, true);
    }
}
