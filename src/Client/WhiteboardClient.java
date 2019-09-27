package Client;

import Client.MouseListeners.PencilListener;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class WhiteboardClient {
    public WhiteboardClientGUI whiteboardClientGUI;

    public WhiteboardClient(){
        whiteboardClientGUI = new WhiteboardClientGUI();
        addMouseListenerToButton();
        whiteboardClientGUI.startGUI();
    }

    private void addMouseListenerToButton() {
        DrawingPanel drawingPanel = whiteboardClientGUI.drawingPanel;
        //TODO: remove null and add real listener
        whiteboardClientGUI.btnText.addMouseListener(null);
        whiteboardClientGUI.btnPencil.addMouseListener(new PencilListener(drawingPanel));
        whiteboardClientGUI.btnEraser.addMouseListener(null);
        whiteboardClientGUI.btnCircle.addMouseListener(null);
    }

    public static void clearDrawingListener(JPanel drawingPanel) {
        for(MouseListener listener : drawingPanel.getMouseListeners()){
            drawingPanel.removeMouseListener(listener);
        }
        for(MouseMotionListener listener : drawingPanel.getMouseMotionListeners()){
            drawingPanel.removeMouseMotionListener(listener);
        }
    }
}
