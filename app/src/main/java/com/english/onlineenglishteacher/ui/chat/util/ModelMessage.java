package com.english.onlineenglishteacher.ui.chat.util;

import com.google.firebase.Timestamp;

public class ModelMessage {

    private String message;
    private String sender;
    private String receiver;
    private String senderName;
    private Timestamp time;
    private boolean read;

    public ModelMessage() {
    }

    public ModelMessage(String message, String sender, String receiver, String senderName, Timestamp time, boolean read) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.senderName = senderName;
        this.time = time;
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
