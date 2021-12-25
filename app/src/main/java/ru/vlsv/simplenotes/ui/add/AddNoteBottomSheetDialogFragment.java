package ru.vlsv.simplenotes.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.vlsv.simplenotes.R;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "AddNoteBottomSheetDialogFragment";

    public static AddNoteBottomSheetDialogFragment newInstance() {
        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();

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
    }
}
