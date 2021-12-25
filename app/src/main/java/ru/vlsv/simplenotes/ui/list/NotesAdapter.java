package ru.vlsv.simplenotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import ru.vlsv.simplenotes.R;
import ru.vlsv.simplenotes.entities.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());

    private ArrayList<Note> data = new ArrayList<>();

    private onClick onClick;

    private final Fragment fragment;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(Collection<Note> notes) {
        data.clear();
        data.addAll(notes);
    }

    public int addItem(Note note) {
        data.add(note);

        return data.size();
    }

    public int removeItem(Note selectedNote) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null && (data.get(i)).getId().equals(selectedNote.getId())) {
                index = i;
                break;
            }
        }

        data.remove(index);
        return index;
    }

    public int updateItem(Note note) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null && (data.get(i)).getId().equals(note.getId())) {
                index = i;

                break;
            }
        }

        data.set(index, note);
        return index;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent,
                false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = data.get(position);

        holder.getNoteTitle().setText(note.getNoteName());
        holder.getNoteText().setText(note.getNoteText());
        holder.getNoteDate().setText(dateFormat.format(note.getDate()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public NotesAdapter.onClick getOnClick() {
        return onClick;
    }

    public void setOnClick(NotesAdapter.onClick onClick) {
        this.onClick = onClick;
    }

    interface onClick {
        void onClick(Note note);

        void onLongClick(Note note);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteDate;

        private TextView noteTitle;

        private TextView noteText;

        public TextView getNoteDate() {
            return noteDate;
        }

        public TextView getNoteTitle() {
            return noteTitle;
        }

        public TextView getNoteText() {
            return noteText;
        }

        public NoteViewHolder(@NonNull View itemView) {

            super(itemView);

            CardView card = itemView.findViewById(R.id.card);

            fragment.registerForContextMenu(card);

            noteDate = itemView.findViewById(R.id.note_date);

            noteTitle = itemView.findViewById(R.id.note_name);

            noteText = itemView.findViewById(R.id.note_text);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Note note = data.get(getAdapterPosition());

                    if (getOnClick() != null) {
                        getOnClick().onClick(note);
                    }
                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    card.showContextMenu();

                    Note note = data.get(getAdapterPosition());


                    if (getOnClick() != null) {
                        getOnClick().onLongClick(note);
                    }

                    return true;
                }
            });
        }
    }

}
