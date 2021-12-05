package ru.vlsv.simplenotes.ui.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.View;
import android.widget.TextView;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;

public class NoteTextFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String RESULT_KEY = "NoteListFragment_RESULT";

    private TextView noteName;
    private TextView noteText;

    public NoteTextFragment() {
        super(R.layout.fragment_note_text);
    }

    public static NoteTextFragment newInstance(Note note) {
        NoteTextFragment fragment = new NoteTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteName = view.findViewById(R.id.note_name_header);

        noteText = view.findViewById(R.id.note_text);

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            displayDetails(getArguments().getParcelable(ARG_NOTE));
        }

        getParentFragmentManager()
                .setFragmentResultListener(RESULT_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(NoteTextFragment.ARG_NOTE);

                        displayDetails(note);
                    }
                });
    }

    private void displayDetails(Note note) {
        noteName.setText(note.getNoteName());
        noteText.setText(note.getNoteText());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}