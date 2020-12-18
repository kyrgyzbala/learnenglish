package com.english.onlineenglishteacher.ui.test.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class ModelQuiz implements Parcelable {

    private String name;
    private int level;
    private int questions;
    @ServerTimestamp
    private Timestamp date;

    public ModelQuiz() {
    }

    public ModelQuiz(String name, int level, int questions) {
        this.name = name;
        this.level = level;
        this.questions = questions;
    }

    protected ModelQuiz(Parcel in) {
        name = in.readString();
        level = in.readInt();
        questions = in.readInt();
        date = in.readParcelable(Timestamp.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeInt(questions);
        dest.writeParcelable(date, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelQuiz> CREATOR = new Creator<ModelQuiz>() {
        @Override
        public ModelQuiz createFromParcel(Parcel in) {
            return new ModelQuiz(in);
        }

        @Override
        public ModelQuiz[] newArray(int size) {
            return new ModelQuiz[size];
        }
    };

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
