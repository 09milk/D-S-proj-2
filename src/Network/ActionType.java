package Network;

import java.io.Serializable;

public enum ActionType implements Serializable {
    DRAW,
    REDO,
    CONNECT,
    DISCONNECT,
    MEMBER_AMOUNT,
    CHANGE_LOCAL_NAME,
    SET_QUEUE,
    NEW_BOARD,
    CLOSE_ROOM,
}