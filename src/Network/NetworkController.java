package Network;

import Client.DrawActions.IDrawAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class NetworkController {

    public String boardName;

    protected ObjectInputStream inputStream;
    protected ObjectOutputStream outputStream;

    public void setIOStream(Socket socket) throws Exception {
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new Exception("Failed to get I/O stream");
        }
    }

    public void sendPackage(IDrawAction drawAction) {
        NetworkPackage networkPackage = new NetworkPackage(ActionType.DRAW, drawAction);
        sendPackage(networkPackage);
    }

    public void sendPackage(NetworkPackage networkPackage) {
        startSending(networkPackage);
        System.out.println("Sending: " + networkPackage);
    }

    abstract public void receivedPackage(NetworkPackage networkPackage);

    protected void startReading() {
        new Thread(new networkInputHandler()).start();
    }

    protected void startSending(NetworkPackage networkPackage) {
        new Thread(new networkOutputHandler(networkPackage)).start();
    }

    private class networkInputHandler implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (inputStream) {
                    receivedPackage((NetworkPackage) inputStream.readObject());
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("networkInputHandler: " + e.getMessage());
            }
        }
    }

    private class networkOutputHandler implements Runnable {
        private NetworkPackage networkPackage;

        public networkOutputHandler(NetworkPackage networkPackage) {
            this.networkPackage = networkPackage;
        }

        @Override
        public void run() {
            try {
                synchronized (outputStream) {
                    outputStream.writeObject(networkPackage);
                }
            } catch (IOException e) {
                System.out.println("networkOutputHandler: " + e.getMessage());
            }
        }
    }
}
