package ru.vlsv.simplenotes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.ui.detail.NoteTextActivity;
import ru.vlsv.simplenotes.ui.detail.NoteTextFragment;
import ru.vlsv.simplenotes.ui.list.NotesListFragment;

public class NotesActivity extends AppCompatActivity {

    private static final String ARG_NOTE = "ARG_NOTE";

    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                showDetails();
            }
        }

        getSupportFragmentManager()
                .setFragmentResultListener(NotesListFragment.RESULT_KEY, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            showDetails();
                        } else {
                            Intent intent = new Intent(NotesActivity.this, NoteTextActivity.class);
                            intent.putExtra(NoteTextActivity.EXTRA_NOTE, selectedNote);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
    }

    private void showDetails() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteTextFragment.ARG_NOTE, selectedNote);
        getSupportFragmentManager()
                .setFragmentResult(NoteTextFragment.KEY_RESULT, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}