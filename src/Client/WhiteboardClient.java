package Client;

import Client.DrawActions.IDrawAction;
import Client.Listeners.MenuBar.File.*;
import Client.Listeners.MenuBar.Style.ColorSelectionListener;
import Client.Listeners.SliderListener;
import Client.Listeners.ToolButton.*;
import Client.Listeners.MainFrameWindowListener;
import Network.ActionType;
import Network.NetworkPackage;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class WhiteboardClient {
    public WhiteboardClientGUI whiteboardClientGUI;
    public ClientNetworkController clientNetworkController;

    public WhiteboardClient(ClientNetworkController clientNetworkController) {
        this(clientNetworkController, 100, 100);
    }

    public WhiteboardClient(ClientNetworkController clientNetworkController, int posX, int posY) {
        this.clientNetworkController = clientNetworkController;
        clientNetworkController.setWhiteboardClient(this);

        whiteboardClientGUI = new WhiteboardClientGUI(posX, posY);
        synchronized (clientNetworkController.actionLock) {
            clientNetworkController.actionLock.notifyAll();
        }
        DrawingPanel drawingPanel = whiteboardClientGUI.drawingPanel;
        drawingPanel.setDrawActions(new ActionQueue(clientNetworkController, drawingPanel));

        ChatRoom chatRoom = whiteboardClientGUI.chatRoom;
        chatRoom.setCNC(clientNetworkController);

        addMouseListenerToButton();
        whiteboardClientGUI.startGUI();
        whiteboardClientGUI.mainFrame.setNetworkController(clientNetworkController);
    }

    public WhiteboardClient(ClientNetworkController clientNetworkController,
                            String title,
                            ArrayList<IDrawAction> realQueue,
                            int posX,
                            int posY) {
        this(clientNetworkController, posX, posY);
        clientNetworkController.sendPackage(new NetworkPackage(ActionType.NEW_BOARD));
        whiteboardClientGUI.mainFrame.setTitle(title);
        whiteboardClientGUI.drawingPanel.drawActions.setWhiteBoardView(realQueue);
    }


    private void addMouseListenerToButton() {
        DrawingPanel drawingPanel = whiteboardClientGUI.drawingPanel;
        JFrame mainFrame = whiteboardClientGUI.mainFrame;

        mainFrame.addWindowListener(new MainFrameWindowListener(clientNetworkController));

        whiteboardClientGUI.btnText.addActionListener(new TextListener(drawingPanel));
        whiteboardClientGUI.btnPencil.addActionListener(new PencilListener(drawingPanel));
        whiteboardClientGUI.btnEraser.addActionListener(new EraserListener(drawingPanel));
        whiteboardClientGUI.btnLine.addActionListener(new LineListener(drawingPanel));
        whiteboardClientGUI.btnCircle.addActionListener(new OvalListener(drawingPanel));
        whiteboardClientGUI.btnRectangle.addActionListener(new RectangleListener(drawingPanel));

        whiteboardClientGUI.jSlider.addMouseListener(new SliderListener(drawingPanel));


        whiteboardClientGUI.mntmNew.addActionListener(new NewListener(mainFrame, clientNetworkController));
        whiteboardClientGUI.mntmOpen.addActionListener(new OpenListener(mainFrame, drawingPanel, clientNetworkController));
        whiteboardClientGUI.mntmSave.addActionListener(new SaveListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmSaveAs.addActionListener(new SaveAsListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmClose.addActionListener(new CloseRoomListener(mainFrame, clientNetworkController));
        whiteboardClientGUI.mntmExit.addActionListener(new ExitListener(mainFrame, clientNetworkController));

        whiteboardClientGUI.mntmColor.addActionListener(new ColorSelectionListener(drawingPanel));
        
        whiteboardClientGUI.btnChatRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				whiteboardClientGUI.chatRoom.setVisible(true);
				System.out.println("Chat Room Window Opened");
			}
		});
    }

}
