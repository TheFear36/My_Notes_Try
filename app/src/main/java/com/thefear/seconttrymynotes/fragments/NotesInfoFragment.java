package com.thefear.seconttrymynotes.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.thefear.seconttrymynotes.R;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.UserNotesRepository;

public class NotesInfoFragment extends Fragment {

    EditText titleNote;
    EditText infoNote;
    MaterialButton saveButton;

    public NotesInfoFragment() {
        super(R.layout.fragments_notes_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleNote = view.findViewById(R.id.name_note);
        infoNote = view.findViewById(R.id.info_note);

        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
        if (getArguments() != null) {
            Note note = getArguments().getParcelable(ARG_NOTE);
            titleNote.setText(note.getTitle());
            infoNote.setText(note.getInfo());
        }

    }

    void saveNote() {
        Note note = new Note(titleNote.getText().toString(), infoNote.getText().toString());

        UserNotesRepository.getInstance().addNote(note);

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NotesInfoFragment fragment = new NotesInfoFragment();
        fragment.setArguments(args);
    }

    private static String ARG_NOTE = "ARG_NOTE";
}
