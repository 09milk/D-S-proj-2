package Server;


import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        loadConfig();
        ServerSocketController socketController = new ServerSocketController(ServerConfig.SERVER_PORT);
        socketController.start();
        Runtime.getRuntime().addShutdownHook(new Thread(socketController::close));
        //loop should be terminated by ctrl-c only
        while (true) {
            Socket socket = socketController.accept();
            new Thread(new RequestHandler(socket)).start();
        }
    }

    public static void loadConfig() {
        try {
            ServerConfig.loadConfig();
            if (ServerConfig.SERVER_PORT > 65535 || ServerConfig.SERVER_PORT < 0) {
                throw new Exception("Wrong Port");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}