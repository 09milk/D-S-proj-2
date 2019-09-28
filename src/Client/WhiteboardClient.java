package Client;

import Client.Listeners.MenuBar.File.*;
import Client.Listeners.MenuBar.Style.ColorSelectionListener;
import Client.Listeners.SliderListener;
import Client.Listeners.ToolButton.EraserListener;
import Client.Listeners.ToolButton.OvalListener;
import Client.Listeners.ToolButton.PencilListener;
import Client.Listeners.ToolButton.TextListener;

import javax.swing.*;

public class WhiteboardClient {
    public static int newFileCount = 0;

    public WhiteboardClientGUI whiteboardClientGUI;

    public ClientNetworkController clientNetworkController;

    public WhiteboardClient(ClientNetworkController clientNetworkController) {
        whiteboardClientGUI = new WhiteboardClientGUI();
        whiteboardClientGUI.drawingPanel.setDrawActions(new ActionQueue(clientNetworkController));
        addMouseListenerToButton();
        whiteboardClientGUI.startGUI();
        whiteboardClientGUI.mainFrame.setTitle("new " + newFileCount);
        newFileCount++;
    }

    private void addMouseListenerToButton() {
        DrawingPanel drawingPanel = whiteboardClientGUI.drawingPanel;
        JFrame mainFrame = whiteboardClientGUI.mainFrame;

        whiteboardClientGUI.btnText.addActionListener(new TextListener(drawingPanel));
        whiteboardClientGUI.btnPencil.addActionListener(new PencilListener(drawingPanel));
        whiteboardClientGUI.btnEraser.addActionListener(new EraserListener(drawingPanel));
        whiteboardClientGUI.btnCircle.addActionListener(new OvalListener(drawingPanel));

        whiteboardClientGUI.jSlider.addMouseListener(new SliderListener(drawingPanel));


        whiteboardClientGUI.mntmNew.addActionListener(new NewListener(mainFrame, clientNetworkController));
        whiteboardClientGUI.mntmOpen.addActionListener(new OpenListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmSave.addActionListener(new SaveListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmSaveAs.addActionListener(new SaveAsListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmExit.addActionListener(new ExitListener(mainFrame));

        whiteboardClientGUI.mntmColor.addActionListener(new ColorSelectionListener(drawingPanel));
    }

}
