package ru.vlsv.simplenotes.repositories;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vlsv.simplenotes.entities.Note;

public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();

    private static final String KEY_NAME = "noteName";
    private static final String KEY_TEXT = "noteText";
    private static final String KEY_CREATED = "noteCreateDate";

    private static final String NOTES = "notes";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getAll(Callback<List<Note>> callback) {
        db.collection(NOTES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot snapshot : documents) {
                            String id = snapshot.getId();

                            String noteName = snapshot.getString(KEY_NAME);
                            String noteText = snapshot.getString(KEY_TEXT);
                            Date noteCreateDate = snapshot.getDate(KEY_CREATED);

                            result.add(new Note(id, noteName, noteText, noteCreateDate));
                        }

                        callback.onSuccess(result);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void save(String noteName, String noteText, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();

        Date noteCreateDate = new Date();

        data.put(KEY_NAME, noteName);
        data.put(KEY_TEXT, noteText);
        data.put(KEY_CREATED, noteCreateDate);

        db.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();

                        callback.onSuccess(new Note(id, noteName, noteText, noteCreateDate));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void update(Note note, String noteName, String noteText, Callback<Note> callback) {

        Map<String, Object> data = new HashMap<>();

        data.put(KEY_NAME, noteName);
        data.put(KEY_TEXT, noteText);
        data.put(KEY_CREATED, note.getDate());

        db.collection(NOTES)
                .document(note.getId())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        note.setNoteName(noteName);
                        note.setNoteText(noteText);

                        callback.onSuccess(note);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });

    }
}
