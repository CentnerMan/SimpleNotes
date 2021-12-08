package ru.vlsv.simplenotes.entities;

import java.util.List;

public interface NotesRepository {

    List<Note> getAllNotes();

    void addNote(Note note);

    void removeNote(Note note);
}
