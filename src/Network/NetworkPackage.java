package Network;

import Client.DrawActions.IDrawAction;

import java.io.Serializable;
import java.util.ArrayList;

public class NetworkPackage implements Serializable {

    public ActionType actionType;
    public IDrawAction drawAction;
    public String roomName;
    public int amountOfMembers;
    public ArrayList<IDrawAction> realQueue;
    public UserName userName;


    public NetworkPackage(ActionType actionType) {
        this.actionType = actionType;
    }

    public NetworkPackage(ActionType actionType, UserName userName, String roomName) {
        this(actionType);
        this.userName = userName;
        this.roomName = roomName;
    }

    public NetworkPackage(ActionType actionType, IDrawAction drawAction) {
        this(actionType);
        this.drawAction = drawAction;
    }

    public NetworkPackage(ActionType actionType, UserName userName, IDrawAction drawAction) {
        this(actionType);
        this.userName = userName;
        this.drawAction = drawAction;
    }

    public NetworkPackage(ActionType actionType, int amountOfMembers) {
        this(actionType);
        this.amountOfMembers = amountOfMembers;
    }

    public NetworkPackage(ActionType actionType, ArrayList<IDrawAction> realQueue) {
        this(actionType);
        this.realQueue = realQueue;
    }

}

