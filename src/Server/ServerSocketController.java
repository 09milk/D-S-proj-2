/*

Name: Marco Ho
Student ID: 791960

*/

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketController {

    private int port;
    private ServerSocket socket;
    private boolean serverStopping = false;

    public ServerSocketController(int port) {
        this.port = port;
    }

    public void start() {
        try {
            socket = new ServerSocket(this.port);
            System.out.printf("Server socket listening in port %d... \n", this.port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void close() {
        if (this.socket != null) {
            try {
                serverStopping = true;
                this.socket.close();
                System.out.println("Stopping server...");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("\nFailed to close server socket.");
            }
        }
    }

    public Socket accept() {
        try {
            return this.socket.accept();
        } catch (IOException e) {
            if (!serverStopping) {
                e.printStackTrace();
                System.out.println("\nFailed to accept");
            }
        }
        return null;
    }
}
