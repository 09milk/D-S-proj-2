package Network;

import Client.DrawActions.IDrawAction;
import Server.ChatHistory;

import java.io.Serializable;
import java.util.ArrayList;

public class NetworkPackage implements Serializable {

    public ActionType actionType;
    public IDrawAction drawAction;
    public String roomName;
    public String boardName;
    public int amountOfMembers;
    public ArrayList<IDrawAction> realQueue;
    public User user;

    public String chatMessage;
    public ChatHistory chatHistory;
    public ArrayList<User> memberList;

    public NetworkPackage(ActionType actionType) {
        this.actionType = actionType;
    }

    public NetworkPackage(ActionType actionType, User user, String string) {
        this(actionType);
        this.user = user;
        switch (actionType) {
            case CONNECT:
                this.roomName = string;
            case CHANGE_BOARD_NAME:
                this.boardName = string;
            case CHAT:
                this.chatMessage = string;
        }
    }

    public NetworkPackage(ActionType actionType, IDrawAction drawAction) {
        this(actionType);
        this.drawAction = drawAction;
    }

    public NetworkPackage(ActionType actionType, User user, IDrawAction drawAction) {
        this(actionType);
        this.user = user;
        this.drawAction = drawAction;
    }

    public NetworkPackage(ActionType actionType, ArrayList<IDrawAction> realQueue) {
        this(actionType);
        this.realQueue = realQueue;
    }

    public NetworkPackage(ChatHistory chatHistory) {
        this(ActionType.CHAT_HISTORY);
        this.chatHistory = new ChatHistory(chatHistory);
    }

    public NetworkPackage(ArrayList<User> memberList) {
        this(ActionType.MEMBER_UPDATE);
        this.memberList = new ArrayList<>();
        for (User user : memberList) {
            this.memberList.add(new User(user));
        }
    }

    public NetworkPackage(ActionType actionType, User user) {
        this.actionType = actionType;
        this.user = user;
    }
}

