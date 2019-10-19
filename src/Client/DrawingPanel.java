package Client;


import Client.DrawActions.IDrawAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingPanel extends JPanel {

    public Color color = Color.BLACK;
    public int drawSize = ClientConfig.DEFAULT_SIZE;
    public ActionQueue drawActions;
    public String currentEditingFilename = null;
    public boolean isInPerfectDraw = false;
    private IDrawAction tmpDrawAction;

    public DrawingPanel() {
        super();
        perfectDrawKeyBind();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (IDrawAction drawAction : drawActions) {
            this.draw((Graphics2D) graphics, drawAction);
        }

        try {
            this.draw((Graphics2D) graphics, tmpDrawAction);
        } catch (NullPointerException ignored) {
        }
    }

    private void draw(Graphics2D graphics, IDrawAction drawAction) {
        drawAction.draw(graphics);
    }

    public void clearDrawingListener() {
        for (MouseListener listener : this.getMouseListeners()) {
            this.removeMouseListener(listener);
        }
        for (MouseMotionListener listener : this.getMouseMotionListeners()) {
            this.removeMouseMotionListener(listener);
        }
    }

    public void setTmpDrawAction(IDrawAction tmpDrawAction) {
        this.tmpDrawAction = tmpDrawAction;
        repaint();
    }

    public void resetTmpDrawAction() {
        setTmpDrawAction(null);
    }

    public void setDrawActions(ActionQueue drawActions) {
        this.drawActions = drawActions;
        repaint();
    }

    protected void perfectDrawKeyBind() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), "pressed SHIFT");
        actionMap.put("pressed SHIFT", new PerfectDrawButtonOnPress());

        inputMap.put(KeyStroke.getKeyStroke("released SHIFT"), "released SHIFT");
        actionMap.put("released SHIFT", new PerfectDrawButtonOnRelease());
    }

    protected class PerfectDrawButtonOnPress extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
            isInPerfectDraw = true;
        }
    }

    protected class PerfectDrawButtonOnRelease extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
            isInPerfectDraw = false;
        }
    }
}
