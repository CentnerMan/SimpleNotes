package ru.vlsv.simplenotes.entities;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Note implements Parcelable {

    private String noteName;

    private String noteText;

//    private LocalDateTime noteCreateDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(String noteName, String noteText) {
        this.noteName = noteName;
        this.noteText = noteText;
//        noteCreateDate = LocalDateTime.now();
    }

    protected Note(Parcel in) {
        noteName = in.readString();
        noteText = in.readString();
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

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

//    public LocalDateTime getNoteCreateDate() {
//        return noteCreateDate;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeString(noteText);
    }
}
