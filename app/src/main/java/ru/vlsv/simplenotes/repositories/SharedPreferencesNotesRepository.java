package ru.vlsv.simplenotes.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ru.vlsv.simplenotes.entities.Note;

public class SharedPreferencesNotesRepository implements NotesRepository {

    @SuppressLint("StaticFieldLeak")
    private static SharedPreferencesNotesRepository INSTANCE;

    private static final String KEY_NOTES = "KEY_NOTES";

    public static SharedPreferencesNotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesNotesRepository(context);
        }
        return INSTANCE;
    }

    private final SharedPreferences preferences;

    private Gson gson = new Gson();

    public SharedPreferencesNotesRepository(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("notes",
                Context.MODE_PRIVATE);
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {
        String data = preferences.getString(KEY_NOTES, "[]");

        Type type = new TypeToken<List<Note>>() {

        }.getType();

        List<Note> result = gson.fromJson(data, type);

        callback.onSuccess(result);
    }

    @Override
    public void save(String noteName, String noteText, Callback<Note> callback) {

        String data = preferences.getString(KEY_NOTES, "[]");

        Type type = new TypeToken<ArrayList<Note>>() {

        }.getType();

        ArrayList<Note> result = gson.fromJson(data, type);

        Note note = new Note(UUID.randomUUID().toString(), noteName, noteText);

        result.add(note);

        String toSave = gson.toJson(result, type);

        preferences.edit().putString(KEY_NOTES, toSave)
                .apply();

        callback.onSuccess(note);
    }

    @Override
    public void update(Note note, String noteName, String noteText, Callback<Note> callback) {

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

    }
}
