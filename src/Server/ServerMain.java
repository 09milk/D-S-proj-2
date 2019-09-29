package Server;

import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        ServerParsedArgs parsedArgs = new ServerParsedArgs(args);
        ServerSocketController socketController = new ServerSocketController(parsedArgs.port);
        socketController.start();
        RoomManager roomManager = RoomManager.getInstance();
        Runtime.getRuntime().addShutdownHook(new Thread(socketController::close));
        //loop should be terminated by ctrl-c only
        while (true) {
            Socket socket = socketController.accept();
            new Thread(new RequestHandler(socket)).start();
        }
    }
}

class ServerParsedArgs {

    int port;

    ServerParsedArgs(String[] args) {
        try {
            this.port = Integer.parseInt(args[0]);
            if (this.port > 65535 || this.port < 0) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println("Usage: server <port>");
            System.exit(1);
        }
    }
}