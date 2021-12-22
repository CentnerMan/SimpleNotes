package ru.vlsv.simplenotes.ui.add;

import android.os.Bundle;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.Callback;
import ru.vlsv.simplenotes.repositories.NotesRepository;


public class AddNotePresenter implements NotePresenter {

    public static final String KEY = "AddNoteBottomSheetDialogFragment_ADDNOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private AddNoteView view;
    private NotesRepository repository;

    public AddNotePresenter(AddNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;

        view.setActionButtonText(R.string.btn_save);
    }

    @Override
    public void onActionPressed(String noteName, String noteText) {
        view.showProgress();

        repository.save(noteName, noteText, new Callback<Note>() {
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
