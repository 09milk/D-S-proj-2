package Server;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {

    private static RoomManager instance = new RoomManager();
    private ConcurrentHashMap<String, Room> allRooms = new ConcurrentHashMap<>();

    private RoomManager() {
    }

    public static RoomManager getInstance() {
        return instance;
    }

    public Room newRoom(String name) {
        Room room = allRooms.putIfAbsent(name, new Room(name));
        if (room == null) {
            room = allRooms.get(name);
        }
        return room;
    }

    public Room getRoom(String name) {
        return newRoom(name);
    }

}
