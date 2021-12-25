package ru.vlsv.simplenotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    public void setData(Collection<Note> notes) {
        data.clear();
        data.addAll(notes);
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
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteDate;

        private TextView noteTitle;

        public TextView getNoteDate() {
            return noteDate;
        }

        public TextView getNoteTitle() {
            return noteTitle;
        }

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteDate = itemView.findViewById(R.id.note_date);

            noteTitle = itemView.findViewById(R.id.note_name);


            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = data.get(getAdapterPosition());

                    if (getOnClick() != null) {
                        getOnClick().onClick(note);
                    }
                }
            });
        }
    }

}
