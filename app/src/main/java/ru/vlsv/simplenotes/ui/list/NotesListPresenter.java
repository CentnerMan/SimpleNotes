package ru.vlsv.simplenotes.ui.list;

import java.util.List;

import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.entities.NotesRepository;

public class NotesListPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void refresh() {

        List<Note> result = repository.getAllNotes();

        view.showNotes(result);
    }
}
