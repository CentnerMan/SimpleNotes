package ru.vlsv.simplenotes.repositories;

import java.util.List;

import ru.vlsv.simplenotes.entities.Note;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);

    void addNote(Note note);

    void removeNote(Note note);
}
