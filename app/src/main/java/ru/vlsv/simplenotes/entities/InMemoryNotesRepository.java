package ru.vlsv.simplenotes.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Note> getAllNotes() {
        ArrayList<Note> result = new ArrayList();

        result.add(new Note("Первая заметка", "Текст первой заметки"));
        result.add(new Note("Вторая заметка", "Текст второй заметки"));

        return result;
    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void removeNote(Note note) {

    }
}
