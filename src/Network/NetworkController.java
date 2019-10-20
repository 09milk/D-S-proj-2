package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class NetworkController {

    public String roomName;

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

    public Thread sendPackage(NetworkPackage networkPackage) {
        log("Sending: " + networkPackage.actionType);
        return startSending(networkPackage);
    }

    public void receivedPackage(NetworkPackage networkPackage) {
        log("Receiving: " + networkPackage.actionType);
    }

    protected void startReading() {
        new Thread(new networkInputHandler()).start();
    }

    protected Thread startSending(NetworkPackage networkPackage) {
        Thread thread = new Thread(new networkOutputHandler(networkPackage));
        thread.start();
        return thread;
    }

    protected void log(String message) {
        System.out.println(message);
    }

    protected void networkErrorHandler() {
    }

    private class networkInputHandler implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (inputStream) {
                    receivedPackage((NetworkPackage) inputStream.readObject());
                }
            } catch (ClassNotFoundException | IOException e) {
                log("networkInputHandler: " + e.getMessage());
                networkErrorHandler();
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
                log("networkOutputHandler: " + e.getMessage());
                networkErrorHandler();
            }
        }
    }
}
