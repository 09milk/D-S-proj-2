package Client;

import Client.MouseListeners.MenuBar.ColorSelectionListener;
import Client.MouseListeners.ToolButton.EraserListener;
import Client.MouseListeners.ToolButton.OvalListener;
import Client.MouseListeners.ToolButton.PencilListener;
import Client.MouseListeners.ToolButton.TextListener;

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
        whiteboardClientGUI.btnText.addMouseListener(new TextListener(drawingPanel));
        whiteboardClientGUI.btnPencil.addMouseListener(new PencilListener(drawingPanel));
        whiteboardClientGUI.btnEraser.addMouseListener(new EraserListener(drawingPanel));
        whiteboardClientGUI.btnCircle.addMouseListener(new OvalListener(drawingPanel));

        whiteboardClientGUI.mnColor.addMouseListener(new ColorSelectionListener(drawingPanel));
    }

}
