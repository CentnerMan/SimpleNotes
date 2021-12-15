package ru.vlsv.simplenotes.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;

public class NoteTextFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NoteTextFragment_KEY_RESULT";
    private TextView noteName;
    private TextView noteText;
    private Note theNote;

    public NoteTextFragment() {
    }

    public NoteTextFragment(Note note) {
        theNote = note;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_text, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteName = view.findViewById(R.id.notes_name);

        noteText = view.findViewById(R.id.notes_text);

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            displayDetails(getArguments().getParcelable(ARG_NOTE));
        } else {
            displayDetails(theNote);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_text_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
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