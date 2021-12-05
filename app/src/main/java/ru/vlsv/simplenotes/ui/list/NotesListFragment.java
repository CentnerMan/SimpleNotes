package ru.vlsv.simplenotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.InMemoryNotesRepository;
import ru.vlsv.simplenotes.entities.Note;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String RESULT_KEY = "NotesListFragment_RESULT";

    private LinearLayout notesContainer;

    private NotesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, new InMemoryNotesRepository());
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

        presenter.refresh();
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

                    getParentFragmentManager()
                            .setFragmentResult(RESULT_KEY, data);

                }
            });

            TextView cityTitle = itemView.findViewById(R.id.note_name);
            cityTitle.setText(note.getNoteName());

            notesContainer.addView(itemView);
        }

    }
}