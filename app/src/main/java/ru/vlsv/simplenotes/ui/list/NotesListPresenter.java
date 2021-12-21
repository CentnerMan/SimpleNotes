package ru.vlsv.simplenotes.ui.list;

import java.util.List;

import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.Callback;
import ru.vlsv.simplenotes.repositories.NotesRepository;

public class NotesListPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {

        view.showProgress();

        repository.getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                view.showNotes(result);

                if (result.isEmpty()) {
                    view.showEmpty();
                } else {
                    view.hideEmpty();
                }

                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
                // Show error
            }
        });

    }

}
