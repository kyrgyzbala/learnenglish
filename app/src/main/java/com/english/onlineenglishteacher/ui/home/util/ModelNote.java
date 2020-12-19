package com.english.onlineenglishteacher.ui.home.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class ModelNote implements Parcelable {

    private String topic;
    private String link;
    @ServerTimestamp
    private Timestamp date;
    private int level;

    public ModelNote() {
    }

    protected ModelNote(Parcel in) {
        topic = in.readString();
        link = in.readString();
        date = in.readParcelable(Timestamp.class.getClassLoader());
        level = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topic);
        dest.writeString(link);
        dest.writeParcelable(date, flags);
        dest.writeInt(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelNote> CREATOR = new Creator<ModelNote>() {
        @Override
        public ModelNote createFromParcel(Parcel in) {
            return new ModelNote(in);
        }

        @Override
        public ModelNote[] newArray(int size) {
            return new ModelNote[size];
        }
    };

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ModelNote(String topic, String link) {
        this.topic = topic;
        this.link = link;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
