package ru.vlsv.simplenotes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.repositories.InMemoryNotesRepository;
import ru.vlsv.simplenotes.ui.add.AddNoteBottomSheetDialogFragment;
import ru.vlsv.simplenotes.ui.detail.NoteTextFragment;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";

    private SwipeRefreshLayout swipeRefreshLayout;

    private CoordinatorLayout root;

    private TextView emptyList;

    private RecyclerView notesList;

    private NotesAdapter notesAdapter;

    private NotesListPresenter presenter;

    private Note selectedNote;

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
        notesAdapter = new NotesAdapter(this);

        notesAdapter.setOnClick(new NotesAdapter.onClick() {
            @Override
            public void onClick(Note note) {
//                Bundle data = new Bundle();
//                data.putParcelable(ARG_NOTE, note);
//
//                getParentFragmentManager()
//                        .beginTransaction()
//                        .addToBackStack("")
//                        .replace(R.id.fragment_container, NoteTextFragment.newInstance(note))
//                        .commit();
                selectedNote = note;

            }

            @Override
            public void onLongClick(Note note) {
                selectedNote = note;
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

        root = view.findViewById(R.id.coordinator);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestNotes();
            }
        });

        notesList = view.findViewById(R.id.notes_list);
        emptyList = view.findViewById(R.id.empty);

//        notesList.setLayoutManager(new LinearLayoutManager(requireContext(),
//                LinearLayoutManager.VERTICAL, false));
        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        notesList.setAdapter(notesAdapter);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteBottomSheetDialogFragment.addInstance()
                        .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);
            }
        });

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
//                DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_divider_horizontal));
//
//        notesList.addItemDecoration(itemDecoration);

//        DividerItemDecoration itemDecorationTwo = new DividerItemDecoration(requireContext(),
//                DividerItemDecoration.HORIZONTAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(),
//                R.drawable.bg_divider_vertical));
//
//        notesList.addItemDecoration(itemDecorationTwo);

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {
        notesAdapter.setData(notes);

        notesAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        emptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyList.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.requestNotes();
                    }
                })
                .show();
    }

    @Override
    public void onNoteAdded(Note note) {
        int index = notesAdapter.addItem(note);

        notesAdapter.notifyItemInserted(index - 1);

        notesList.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {
        int index = notesAdapter.removeItem(selectedNote);

        notesAdapter.notifyItemRemoved(index);
    }

    @Override
    public void onNoteUpdated(Note note) {
        int index = notesAdapter.updateItem(note);

        notesAdapter.notifyItemChanged(index);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            presenter.removeNote(selectedNote);
            return true;
        }

        if (item.getItemId() == R.id.action_update) {
            AddNoteBottomSheetDialogFragment.updateInstance(selectedNote)
                    .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);

            return true;
        }

        return super.onContextItemSelected(item);
    }
}