package Client;

import Client.Listeners.MenuBar.File.OpenListener;
import Client.Listeners.MenuBar.File.SaveAsListener;
import Client.Listeners.MenuBar.Style.ColorSelectionListener;
import Client.Listeners.SliderListener;
import Client.Listeners.ToolButton.EraserListener;
import Client.Listeners.ToolButton.OvalListener;
import Client.Listeners.ToolButton.PencilListener;
import Client.Listeners.ToolButton.TextListener;

import javax.swing.*;

public class WhiteboardClient {
    public WhiteboardClientGUI whiteboardClientGUI;

    public WhiteboardClient() {
        whiteboardClientGUI = new WhiteboardClientGUI();
        addMouseListenerToButton();
        whiteboardClientGUI.startGUI();
    }

    private void addMouseListenerToButton() {
        DrawingPanel drawingPanel = whiteboardClientGUI.drawingPanel;
        JFrame mainFrame = whiteboardClientGUI.mainFrame;

        whiteboardClientGUI.btnText.addActionListener(new TextListener(drawingPanel));
        whiteboardClientGUI.btnPencil.addActionListener(new PencilListener(drawingPanel));
        whiteboardClientGUI.btnEraser.addActionListener(new EraserListener(drawingPanel));
        whiteboardClientGUI.btnCircle.addActionListener(new OvalListener(drawingPanel));

        whiteboardClientGUI.jSlider.addMouseListener(new SliderListener(drawingPanel));


        whiteboardClientGUI.mntmOpen.addActionListener(new OpenListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmSave.addActionListener(null);
        whiteboardClientGUI.mntmSaveAs.addActionListener(new SaveAsListener(mainFrame, drawingPanel));

        whiteboardClientGUI.mntmColor.addActionListener(new ColorSelectionListener(drawingPanel));
    }

}
