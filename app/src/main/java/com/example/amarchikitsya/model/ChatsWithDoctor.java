package com.example.amarchikitsya.model;

public class ChatsWithDoctor {

    String chat_id,msg,receiver_id,sender_id;
    long timestamp;

    public ChatsWithDoctor() {
    }

    public ChatsWithDoctor(String chat_id, String msg, String receiver_id, String sender_id, long timestamp) {
        this.chat_id = chat_id;
        this.msg = msg;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.timestamp = timestamp;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getMsg() {
        return msg;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
