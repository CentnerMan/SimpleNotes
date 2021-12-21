package ru.vlsv.simplenotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.InMemoryNotesRepository;
import ru.vlsv.simplenotes.ui.detail.NoteTextFragment;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";

    private ProgressBar progressBar;

    private RecyclerView notesList;

    private NotesAdapter notesAdapter;

    private NotesListPresenter presenter;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());

    public NotesListFragment() {
    }

    public static NotesListFragment newInstance(Note note) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, InMemoryNotesRepository.INSTANCE);
        notesAdapter = new NotesAdapter();

        notesAdapter.setOnClick(new NotesAdapter.onClick() {
            @Override
            public void onClick(Note note) {
                Bundle data = new Bundle();
                data.putParcelable(ARG_NOTE, note);

                getParentFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.fragment_container, NoteTextFragment.newInstance(note))
                        .commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesList = view.findViewById(R.id.notes_list);
        progressBar = view.findViewById(R.id.progress);

//        notesList.setLayoutManager(new LinearLayoutManager(requireContext(),
//                LinearLayoutManager.VERTICAL, false));
        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        notesList.setAdapter(notesAdapter);

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {
        notesAdapter.setData(notes);

        notesAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}