package Server;

import Client.DrawActions.IDrawAction;

import java.net.Socket;

public class RequestHandler implements Runnable {

    public Socket socket;
    private WhiteboardManager whiteboardManager;
    private ServerNetworkController serverNetworkController;
    private ServerWhiteboard serverWhiteboard;
    private HandlerListener handlerListener = new HandlerListener();

    public RequestHandler(Socket socket) {
        this.socket = socket;
        this.whiteboardManager = WhiteboardManager.getInstance();
    }

    @Override
    public void run() {
        serverNetworkController = new ServerNetworkController(this);
    }

    public void linkWhiteboard(String boardName){
        serverWhiteboard = whiteboardManager.getWhiteboard(boardName);
        serverWhiteboard.addListener(handlerListener);
    }

    public void unlinkWhiteboard(){
        serverWhiteboard.removeListener(handlerListener);
    }

    public void addDrawAction(IDrawAction drawAction){
        serverWhiteboard.addDrawAction(drawAction);
    }

    public class HandlerListener {
        public void newDrawAction(IDrawAction drawAction){
            serverNetworkController.sendPackage(drawAction);
        }

        public void sendAmountOfMember(int amountOfMembers){
            serverNetworkController.sendPackage(amountOfMembers);
        }
    }
}
