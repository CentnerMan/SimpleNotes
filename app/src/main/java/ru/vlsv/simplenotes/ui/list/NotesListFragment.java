package ru.vlsv.simplenotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.InMemoryNotesRepository;
import ru.vlsv.simplenotes.ui.detail.NoteTextFragment;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";

    private LinearLayout notesContainer;

    private NotesListPresenter presenter;

    private ProgressBar progressBar;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesContainer = view.findViewById(R.id.notes_container);

        presenter.requestNotes();

        progressBar = view.findViewById(R.id.progress);
    }

    @Override
    public void showNotes(List<Note> notes) {

        for (Note note : notes) {

            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_notes, notesContainer, false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle data = new Bundle();
                    data.putParcelable(ARG_NOTE, note);

//                    Toast.makeText(requireContext(), note.getNoteName(), Toast.LENGTH_SHORT).show();

                    getParentFragmentManager()
                            .beginTransaction()
                            .addToBackStack("")
                            .replace(R.id.fragment_container, NoteTextFragment.newInstance(note))
                            .commit();
                }
            });

            TextView noteDate = itemView.findViewById(R.id.note_date);
            noteDate.setText(dateFormat.format(note.getDate()));

            TextView noteTitle = itemView.findViewById(R.id.note_name);
            noteTitle.setText(note.getNoteName());

            notesContainer.addView(itemView);
        }

    }

    @Override
    public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        progressBar.setVisibility(View.GONE);
    }
}