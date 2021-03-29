package com.example.stopnjot;

public class Note {
    String mId, mMessage, mDate;

    public Note() {

    }

    public Note(String id, String message, String date) {
        this.mId = id;
        this.mMessage = message;
        this.mDate = date;
    }

    public String getId() {
        return mId;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getDate() {
        return mDate;
    }

    public void setId(String id) { this.mId = id; }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public void setDate(String date) {
        this.mDate = date;
    }
}
