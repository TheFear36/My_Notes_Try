package com.thefear.seconttrymynotes.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.thefear.seconttrymynotes.R;
import com.thefear.seconttrymynotes.contracts.ToolbarForActivity;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.UserNotesRepository;

public class NotesInfoFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    private EditText titleNote;
    private EditText infoNote;

    public NotesInfoFragment() {
        super(R.layout.fragments_notes_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.info_toolbar);
        if (getActivity() instanceof ToolbarForActivity) {
            ToolbarForActivity drawer = (ToolbarForActivity) getActivity();
            drawer.setToolbar(toolbar);
        }
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                Toast.makeText(requireContext(), R.string.share, Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.action_save) {
                saveNote();
                return true;
            }
            return false;
        });

        titleNote = view.findViewById(R.id.name_note);
        infoNote = view.findViewById(R.id.info_note);

        if (getArguments() != null) {
            Note note = getArguments().getParcelable(ARG_NOTE);
            titleNote.setText(note.getTitle());
            infoNote.setText(note.getInfo());
        }

        MaterialButton saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(view1 -> saveNote());
    }

    void saveNote() {
        Note note = new Note(titleNote.getText().toString(), infoNote.getText().toString());

        UserNotesRepository.getInstance().addNote(note);

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NotesInfoFragment fragment = new NotesInfoFragment();
        fragment.setArguments(args);
    }
}
