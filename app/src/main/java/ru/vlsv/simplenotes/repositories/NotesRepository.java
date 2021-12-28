package ru.vlsv.simplenotes.repositories;

import java.util.List;

import ru.vlsv.simplenotes.entities.Note;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);

    void save(String noteName, String noteText, Callback<Note> callback);

    void update(Note note, String noteName, String noteText, Callback<Note> callback);

    void delete(Note note, Callback<Void> callback);
}
