package ru.vlsv.simplenotes.ui.list;

import java.util.List;

import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.entities.NotesRepository;

public class NotesListPresenter {

    private NotesListView listView;

    private NotesRepository repository;

    public NotesListPresenter(NotesListView listView, NotesRepository repository) {
        this.listView = listView;
        this.repository = repository;
    }

    public void refresh() {
        List<Note> result = repository.getAllNotes();
        listView.showNotes(result);
    }
}
