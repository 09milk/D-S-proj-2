package Network;

import Client.DrawActions.IDrawAction;

import java.io.Serializable;

public class NetworkPackage implements Serializable {

    public ActionType actionType;
    public IDrawAction drawAction;
    public String boardName;
    public int amountOfMembers;

    public NetworkPackage(ActionType actionType, String boardName) {
        this.actionType = actionType;
        this.boardName = boardName;
    }

    public NetworkPackage(ActionType actionType, IDrawAction drawAction) {
        this.actionType = actionType;
        this.drawAction = drawAction;
    }

    public NetworkPackage(int amountOfMembers) {
        this.actionType = ActionType.MEMBER_AMOUNT;
        this.amountOfMembers = amountOfMembers;
    }

    public NetworkPackage(ActionType actionType) {
        this.actionType = actionType;
    }
}

