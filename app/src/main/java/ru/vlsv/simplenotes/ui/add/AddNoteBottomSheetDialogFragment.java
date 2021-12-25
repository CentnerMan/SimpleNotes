package ru.vlsv.simplenotes.ui.add;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.InMemoryNotesRepository;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment implements AddNoteView {

    public static final String TAG = "AddNoteBottomSheetDialogFragment";
    private static final String ARG_NOTE = "ARG_NOTE";

    private Button btnSave;
    private ProgressBar progressBar;
    private EditText editNoteName;
    private EditText editNoteText;
    private NotePresenter presenter;

    public static AddNoteBottomSheetDialogFragment addInstance() {
        return new AddNoteBottomSheetDialogFragment();
    }

    public static AddNoteBottomSheetDialogFragment updateInstance(Note note) {
        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        btnSave = view.findViewById(R.id.btn_save);

        editNoteName = view.findViewById(R.id.name);
        editNoteText = view.findViewById(R.id.text);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onActionPressed(editNoteName.getText().toString(), editNoteText.getText().toString());
            }
        });


        if (getArguments() == null) {
            presenter = new AddNotePresenter(this, InMemoryNotesRepository.INSTANCE);
        } else {
            Note note = getArguments().getParcelable(ARG_NOTE);
            presenter = new UpdateNotePresenter(this, InMemoryNotesRepository.INSTANCE, note);
        }
    }

    @Override
    public void showProgress() {
        btnSave.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        btnSave.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonText(int title) {
        btnSave.setText(title);
    }

    @Override
    public void setName(String title) {
        editNoteName.setText(title);
    }

    @Override
    public void setText(String message) {
        editNoteText.setText(message);
    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {

        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        dismiss();
    }

}
