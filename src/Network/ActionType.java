package Network;

import java.io.Serializable;

public enum ActionType implements Serializable {
    DRAW,
    REDO,
    CONNECT,
    DISCONNECT,
    MEMBER_UPDATE,
    CHANGE_BOARD_NAME,
    SET_QUEUE,
    NEW_BOARD,
    CHAT,
    CHAT_HISTORY,
    CLOSE_ROOM
}
