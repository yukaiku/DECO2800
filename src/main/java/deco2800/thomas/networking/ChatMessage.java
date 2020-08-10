package deco2800.thomas.networking;

public class ChatMessage {
    String message;
    String username;
    long time;

    public ChatMessage() {

    }

    public ChatMessage(String username, String message) {
        this.message = message;
        this.username = username;
        this.time = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", username, message);
    }
}