package Server;

import java.util.concurrent.ConcurrentHashMap;

public class WhiteboardManager {

    private static WhiteboardManager instance = new WhiteboardManager();
    private ConcurrentHashMap<String, ServerWhiteboard> allWhiteboard = new ConcurrentHashMap<>();

    private WhiteboardManager() {}

    public static WhiteboardManager getInstance() {
        return instance;
    }

    public ServerWhiteboard newWhiteboard(String name){
        ServerWhiteboard whiteboard = allWhiteboard.putIfAbsent(name, new ServerWhiteboard());
        if (whiteboard == null){
            whiteboard = allWhiteboard.get(name);
        }
        return whiteboard;
    }

    public ServerWhiteboard getWhiteboard(String name){
        return newWhiteboard(name);
    }

}
