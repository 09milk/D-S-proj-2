package Client;

import Network.ActionType;
import Network.NetworkPackage;

import javax.swing.*;

public class JFrameNetwork extends JFrame {
    private ClientNetworkController networkController;

    public JFrameNetwork() {
        super();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        if (networkController != null) {
            networkController.sendPackage(new NetworkPackage(
                    ActionType.CHANGE_BOARD_NAME,
                    networkController.user,
                    title));
        }
    }

    public void setTitle(String title, boolean NoSentPackage) {
        if (NoSentPackage) {
            super.setTitle(title);
        } else {
            setTitle(title);
        }
    }

    public void setNetworkController(ClientNetworkController networkController) {
        this.networkController = networkController;
    }
}
