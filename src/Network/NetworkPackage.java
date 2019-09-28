package Network;

import Client.DrawActions.IDrawAction;

import static Network.ActionType.BOARD_CONNECTION;

public class NetworkPackage {

    public ActionType actionType;
    public IDrawAction drawAction;
    public String boardName;

    public NetworkPackage(String boardName) {
        this.actionType = BOARD_CONNECTION;
        this.boardName = boardName;
    }

    public NetworkPackage(ActionType actionType, IDrawAction drawAction) {
        this.actionType = actionType;
        this.drawAction = drawAction;
    }

}

