package ru.vlsv.simplenotes.repositories;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.vlsv.simplenotes.entities.Note;

public class InMemoryNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new InMemoryNotesRepository();

    private Executor executor = Executors.newSingleThreadExecutor();

    private ArrayList<Note> notes = new ArrayList<>();

    private Handler handler = new Handler(Looper.getMainLooper());

    private InMemoryNotesRepository() {
        notes.add(new Note(UUID.randomUUID().toString(), "Первая заметка", "Текст первой заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Вторая заметка", "Текст второй заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Третья заметка", "Текст третьей заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Четвертая заметка", "Текст четвертой заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Пятая заметка", "Текст пятой заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Шестая заметка", "Текст шестой заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Седьмая заметка", "Текст седьмой заметки"));
        notes.add(new Note(UUID.randomUUID().toString(), "Восьмая заметка", "Текст восьмой заметки"));
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(notes);
                    }
                });
            }
        });
    }

    @Override
    public void save(String noteName, String noteText, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Note note = new Note(UUID.randomUUID().toString(), noteName, noteText);

                        notes.add(note);

                        callback.onSuccess(note);
                    }
                });
            }
        });
    }

    @Override
    public void update(String noteId, String noteName, String noteText, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        int index = 0;

                        for (int i = 0; i < notes.size(); i++) {
                            if (notes.get(i).getId().equals(noteId)) {
                                index = i;
                                break;
                            }
                        }

                        Note editableNote = notes.get(index);

                        editableNote.setNoteName(noteName);
                        editableNote.setNoteText(noteText);

                        callback.onSuccess(editableNote);
                    }
                });
            }
        });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        notes.remove(note);

                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

}
