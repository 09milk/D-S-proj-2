package Client.Listeners.ToolButton;

import Client.DrawActions.TextDraw;
import Client.DrawingPanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;

public class TextListener extends AbstractToolButtonListener {

    private int x;
    private int y;
    private boolean writing = false;
    private KeyListener keyListener = new TextKeyListener();
    private ArrayDeque<Character> textString = new ArrayDeque<>();

    private TextDraw tmpDrawAction;

    private TextDrawingListener textDrawingListener = new TextDrawingListener();
    private TextFocusListener textFocusListener = new TextFocusListener();

    public TextListener(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    protected MouseInputAdapter getDrawingListener() {
        return textDrawingListener;
    }

    private String buildString(ArrayDeque<Character> textString) {
        StringBuilder stringBuilder = new StringBuilder(textString.size());
        for (Character c : textString) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    protected class TextDrawingListener extends MouseInputAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            if (writing) {
                //TODO: do this again when changing draw tool
                endTextDrawing();
            } else {
                drawingPanel.requestFocusInWindow();
                writing = true;
                x = event.getX();
                y = event.getY();
                tmpDrawAction = null;
                drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                textString.clear();
                drawingPanel.addKeyListener(keyListener);
                drawingPanel.addFocusListener(textFocusListener);
            }
        }

        private void endTextDrawing() {
            writing = false;
            drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            drawingPanel.removeKeyListener(keyListener);
            drawingPanel.removeFocusListener(textFocusListener);
            drawingPanel.tmpDrawAction = null;
            if (!textString.isEmpty()) {
                drawingPanel.drawActions.add(tmpDrawAction);
            }
            drawingPanel.repaint();
        }
    }

    protected class TextKeyListener extends KeyAdapter {

        public TextKeyListener() {
            super();
        }


        @Override
        public void keyTyped(KeyEvent event) {
            Character character = event.getKeyChar();
            int keyCode = (int) character;
            if (keyCode == KeyEvent.VK_BACK_SPACE) {
                textString.pollLast();
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                textString.clear();
            } else if (character != KeyEvent.CHAR_UNDEFINED) {
                textString.addLast(character);
            }
            tmpDrawAction = new TextDraw(drawingPanel.color, x, y, drawingPanel.size, buildString(textString));
            drawingPanel.tmpDrawAction = tmpDrawAction;

            drawingPanel.repaint();
        }
    }

    protected class TextFocusListener extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent event) {
            textDrawingListener.endTextDrawing();
        }
    }
}
