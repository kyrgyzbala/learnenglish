package com.english.onlineenglishteacher.ui.chat.util;

import com.google.firebase.Timestamp;

public class ModelChat {

    private String userId;
    private String teacherId;
    private String lastMessage;
    private String lastMessageSender;
    private Timestamp lastMessageTime;
    private boolean lastMessageRead;

    public ModelChat() {
    }

    public ModelChat(String userId, String teacherId, String lastMessage, String lastMessageSender, Timestamp lastMessageTime, boolean lastMessageRead) {
        this.userId = userId;
        this.teacherId = teacherId;
        this.lastMessage = lastMessage;
        this.lastMessageSender = lastMessageSender;
        this.lastMessageTime = lastMessageTime;
        this.lastMessageRead = lastMessageRead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageSender() {
        return lastMessageSender;
    }

    public void setLastMessageSender(String lastMessageSender) {
        this.lastMessageSender = lastMessageSender;
    }

    public Timestamp getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Timestamp lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public boolean isLastMessageRead() {
        return lastMessageRead;
    }

    public void setLastMessageRead(boolean lastMessageRead) {
        this.lastMessageRead = lastMessageRead;
    }

    @Override
    public String toString() {
        return "ModelChat{" +
                "userId='" + userId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", lastMessageSender='" + lastMessageSender + '\'' +
                ", lastMessageTime=" + lastMessageTime +
                ", lastMessageRead=" + lastMessageRead +
                '}';
    }
}
