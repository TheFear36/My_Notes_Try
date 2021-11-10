package com.thefear.seconttrymynotes.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thefear.seconttrymynotes.R;

import java.util.ArrayList;
import java.util.Collection;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final ArrayList<Note> notes = new ArrayList<>();

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private OnNoteClicked noteClicked;

    public void setNotes(Collection<Note> toSet) {
        notes.clear();
        notes.addAll(toSet);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);


        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note note = notes.get(position);

        holder.title.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                if (getNoteClicked() != null) {
                    getNoteClicked().onNoteClicked(notes.get(getAdapterPosition()));
                }
            });

            title = itemView.findViewById(R.id.title_note);
        }
    }

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

}
