package com.english.onlineenglishteacher.ui.video;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class ModelVideo {

    private String link;
    private String description;
    @ServerTimestamp
    private Timestamp date;
    private int level;

    public ModelVideo() {
    }

    public ModelVideo(String link, String description, int level) {
        this.link = link;
        this.description = description;
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
