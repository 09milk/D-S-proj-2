package Client;

import Client.DrawActions.IDrawAction;
import Client.Listeners.MainFrameWindowListener;
import Client.Listeners.MenuBar.File.*;
import Client.Listeners.MenuBar.Privilege.BecomeManagerListener;
import Client.Listeners.MenuBar.Privilege.KickUserListener;
import Client.Listeners.MenuBar.Style.ColorSelectionListener;
import Client.Listeners.SliderListener;
import Client.Listeners.ToolButton.*;
import Network.ActionType;
import Network.NetworkPackage;
import Network.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class WhiteboardClient {
    public final Object managerFunctionsActionLock = new Object();
    public WhiteboardClientGUI whiteboardClientGUI;
    public ClientNetworkController clientNetworkController;
    public JMenuItem[] managerFunctions;

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
        addManagerFunctionToList();
        if (clientNetworkController.user.isManager) {
            enableManagerFunctions();
        } else {
            disableManagerFunctions();
        }
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
        ChatRoom chatRoom = whiteboardClientGUI.chatRoom;

        mainFrame.addWindowListener(new MainFrameWindowListener(clientNetworkController));

        whiteboardClientGUI.btnText.addActionListener(new TextListener(drawingPanel));
        whiteboardClientGUI.btnPencil.addActionListener(new PencilListener(drawingPanel));
        whiteboardClientGUI.btnEraser.addActionListener(new EraserListener(drawingPanel));
        whiteboardClientGUI.btnLine.addActionListener(new LineListener(drawingPanel));
        whiteboardClientGUI.btnCircle.addActionListener(new OvalListener(drawingPanel));
        whiteboardClientGUI.btnRectangle.addActionListener(new RectangleListener(drawingPanel));

        whiteboardClientGUI.jSlider.addMouseListener(new SliderListener(drawingPanel));


        whiteboardClientGUI.mntmNew.addActionListener(new NewListener(mainFrame, chatRoom, clientNetworkController));
        whiteboardClientGUI.mntmOpen.addActionListener(new OpenListener(mainFrame, chatRoom, drawingPanel, clientNetworkController));
        whiteboardClientGUI.mntmSave.addActionListener(new SaveListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmSaveAs.addActionListener(new SaveAsListener(mainFrame, drawingPanel));
        whiteboardClientGUI.mntmClose.addActionListener(new CloseRoomListener(mainFrame, clientNetworkController));
        whiteboardClientGUI.mntmExit.addActionListener(new ExitListener(mainFrame, clientNetworkController));

        whiteboardClientGUI.mntmColor.addActionListener(new ColorSelectionListener(drawingPanel));

        whiteboardClientGUI.mntmBecomeManager.addActionListener(new BecomeManagerListener(clientNetworkController));

        whiteboardClientGUI.btnChatRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                whiteboardClientGUI.chatRoom.setVisible(true);
                System.out.println("Chat Room Window Opened");
            }
        });
    }

    private void addManagerFunctionToList() {
        managerFunctions = new JMenuItem[]{
                whiteboardClientGUI.mntmNew,
                whiteboardClientGUI.mntmOpen,
                whiteboardClientGUI.mntmSave,
                whiteboardClientGUI.mntmSaveAs,
                whiteboardClientGUI.mntmClose,
                whiteboardClientGUI.mnAcceptUser,
                whiteboardClientGUI.mnKickUser
        };
        synchronized (managerFunctionsActionLock) {
            managerFunctionsActionLock.notifyAll();
        }
    }

    public void updateMemberList(ArrayList<User> memberList) {
        whiteboardClientGUI.mntmBecomeManager.setEnabled(true);
        whiteboardClientGUI.mnKickUser.removeAll();
        for (User user : memberList) {
            if (user.isManager) {
                whiteboardClientGUI.mntmBecomeManager.setEnabled(false);
                if (user.uuid.equals(clientNetworkController.user.uuid)) {
                    clientNetworkController.user.isManager = true;
                    enableManagerFunctions();
                }
            }
            JMenuItem selectUserButton = new JMenuItem(user.nameWithId);
            selectUserButton.addActionListener(new KickUserListener(clientNetworkController, user));
            whiteboardClientGUI.mnKickUser.add(selectUserButton);
        }
        whiteboardClientGUI.chatRoom.updateMemberList(memberList);
    }

    public void enableManagerFunctions() {
        while (managerFunctions == null) {
            try {
                synchronized (managerFunctionsActionLock) {
                    managerFunctionsActionLock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (JMenuItem menuItem : managerFunctions) {
            menuItem.setEnabled(true);
        }
    }

    public void disableManagerFunctions() {
        for (JMenuItem menuItem : managerFunctions) {
            menuItem.setEnabled(false);
        }
    }
}
