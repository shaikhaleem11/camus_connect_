package com.mcet.campus_connect;

public class Message {
    public String message;
    public String senderId;

    public Message() {
        // default constructor required for Firebase deserialization
    }

    public Message(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }
}
