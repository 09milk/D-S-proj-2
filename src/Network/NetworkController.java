package Network;

import Client.DrawActions.IDrawAction;

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

    public void sendPackage(NetworkPackage networkPackage) {
        startSending(networkPackage);
        log("Sending: " + networkPackage.actionType);
    }

    public void receivedPackage(NetworkPackage networkPackage){
        log("Receiving: " + networkPackage.actionType);
    }

    protected void startReading() {
        new Thread(new networkInputHandler()).start();
    }

    protected void startSending(NetworkPackage networkPackage) {
        new Thread(new networkOutputHandler(networkPackage)).start();
    }

    protected void log(String message){
        System.out.println(message);
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

    protected void networkErrorHandler(){}
}
