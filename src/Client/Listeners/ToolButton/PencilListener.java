package Client.Listeners.ToolButton;

import Client.DrawingPanel;

import java.awt.*;

public class PencilListener extends AbstractFreeDrawListener {

    public PencilListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected Color getColor() {
        return drawingPanel.color;
    }
}
