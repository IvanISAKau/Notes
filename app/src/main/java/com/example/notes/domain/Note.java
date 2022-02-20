package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {

    private final String id;
    private String title;
    private String noteBody;
    private Date date;

    public Note(String id, String title, String noteBody) {
        this.id = id;
        this.title = title;
        this.noteBody = noteBody;
        this.date = new Date();
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        noteBody = in.readString();
        date = new Date(in.readLong());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(noteBody);
        parcel.writeLong(date.getTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
