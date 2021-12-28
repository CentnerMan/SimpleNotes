package ru.vlsv.simplenotes.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;
import ru.vlsv.simplenotes.ui.add.AddNoteBottomSheetDialogFragment;
import ru.vlsv.simplenotes.ui.auth.AuthFragment;
import ru.vlsv.simplenotes.ui.list.NotesListFragment;
import ru.vlsv.simplenotes.ui.preferences.AboutFragment;
import ru.vlsv.simplenotes.ui.preferences.PreferencesFragment;

public class NotesActivity extends AppCompatActivity {

    private static final String ARG_NOTE = "ARG_NOTE";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int NOTIFICATION_ID = 1;

    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbarAndDrawer();

        NotificationChannelCompat channelCompat =
                new NotificationChannelCompat.Builder(CHANNEL_ID,
                        NotificationManagerCompat.IMPORTANCE_DEFAULT)
                        .setDescription(String.valueOf(R.string.channel_description))
                        .setName(getString(R.string.channel_name))
                        .build();
        NotificationManagerCompat.from(this).createNotificationChannel(channelCompat);

        if (savedInstanceState == null) {

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            if (account == null) {
                showAuth();
            } else {
                showNotesList();
            }
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);
        }

        getSupportFragmentManager().setFragmentResultListener(AuthFragment.KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                showNotesList();
            }
        });

//        getSupportFragmentManager()
//                .beginTransaction()
////                .addToBackStack("")
//                .replace(R.id.fragment_container, new NotesListFragment())
//                .commit();
    }

    private void showNotesList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NotesListFragment())
                .commit();
    }

    private void showAuth() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AuthFragment())
                .commit();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
//                Toast.makeText(this, R.string.add_msg, Toast.LENGTH_SHORT).show();
//                Snackbar.make(findViewById(R.id.drawer_layout), R.string.add_msg, Snackbar.LENGTH_SHORT).show();
                AddNoteBottomSheetDialogFragment.addInstance()
                        .show(getSupportFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);
                return true;
//            case R.id.action_rename:
//                showAlertDialog();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    @SuppressLint("NonConstantResourceId")
    private void initDrawer(Toolbar toolbar) {

        // Находим DrawerLayout
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {

                case R.id.action_drawer_about:
                    openAboutFragment();
                    return true;

                case R.id.action_drawer_preferences:
                    openPreferencesFragment();
                    return true;

                case R.id.action_notification:
                    showNotification();
                    return true;

                case R.id.action_drawer_exit:
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container, new AboutFragment()).commit();
    }

    private void openPreferencesFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container, new PreferencesFragment())
                .commit();
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_add_24)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NotesActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NotesActivity.this, R.string.no, Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showNotification() {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Notification compat = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content_text))
                .setSmallIcon(R.drawable.ic_stat_name)
                .build();

        notificationManager.notify(NOTIFICATION_ID, compat);
    }
}