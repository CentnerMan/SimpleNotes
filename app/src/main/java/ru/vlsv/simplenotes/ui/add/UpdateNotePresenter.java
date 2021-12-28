package ru.vlsv.simplenotes.ui.add;

import android.os.Bundle;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.Callback;
import ru.vlsv.simplenotes.repositories.NotesRepository;

public class UpdateNotePresenter implements NotePresenter {
    public static final String KEY = "AddNoteBottomSheetDialogFragment_UPDATENOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private AddNoteView view;
    private NotesRepository repository;
    private Note note;

    public UpdateNotePresenter(AddNoteView view, NotesRepository repository, Note note) {
        this.view = view;
        this.repository = repository;
        this.note = note;


        view.setActionButtonText(R.string.action_update);

        view.setName(note.getNoteName());
        view.setText(note.getNoteText());
    }

    @Override
    public void onActionPressed(String noteName, String noteText) {
        view.showProgress();

        repository.update(note, noteName, noteText, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });


    }
}
