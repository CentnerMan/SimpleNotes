package ru.vlsv.simplenotes.entities;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Note implements Parcelable {

    private String noteName;

    private String noteText;

    private final LocalDateTime noteCreateDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(String noteName, String noteText) {
        this.noteName = noteName;
        this.noteText = noteText;
        noteCreateDate = LocalDateTime.now();
    }

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

    public LocalDateTime getNoteCreateDate() {
        return noteCreateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
