package ru.vlsv.simplenotes.repositories;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Note> getAllNotes() {
        ArrayList<Note> result = new ArrayList<>();

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
