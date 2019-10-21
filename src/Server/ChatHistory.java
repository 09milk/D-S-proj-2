package Server;

import Network.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatHistory implements Serializable {
    private ArrayList<User> chatUsers;
    private ArrayList<String> chatMessages;

    public ChatHistory() {
        chatUsers = new ArrayList<User>();
        chatMessages = new ArrayList<String>();
    }

    public ChatHistory(ChatHistory clone) {
        this.chatUsers = new ArrayList<User>(clone.chatUsers);
        this.chatMessages = new ArrayList<String>(clone.chatMessages);
    }

    public void addChat(User user, String chatMessage) {
        chatUsers.add(user);
        chatMessages.add(chatMessage);
    }

    public int getNumOfChat() {
        return this.chatMessages.size();
    }

    public User getChatUser(int index) {
        return this.chatUsers.get(index);
    }

    public String getChatMessage(int index) {
        return this.chatMessages.get(index);
    }
}