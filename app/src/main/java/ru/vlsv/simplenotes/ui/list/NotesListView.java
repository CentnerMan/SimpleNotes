package ru.vlsv.simplenotes.ui.list;

import java.util.List;

import ru.vlsv.simplenotes.entities.Note;

public interface NotesListView {

    void showNotes(List<Note> notes);

    void showProgress();

    void hideProgress();

    void showEmpty();

    void hideEmpty();
}
