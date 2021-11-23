package com.thefear.seconttrymynotes.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.thefear.seconttrymynotes.R;
import com.thefear.seconttrymynotes.contracts.ToolbarForActivity;
import com.thefear.seconttrymynotes.domain.Callback;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.UserNotesRepository;

import java.util.UUID;

public class NotesInfoFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";

    public static final String KEY_RESULT = "KEY_RESULTNotesInfoFragment";
    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_INFO = "ARG_INFO";

    public static NotesInfoFragment newInstance(Note note) {
        NotesInfoFragment fragment = new NotesInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText titleNote;
    private EditText infoNote;

    public NotesInfoFragment() {
        super(R.layout.fragments_notes_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton saveButton = view.findViewById(R.id.save_button);

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

            saveButton.setOnClickListener(view1 -> updateNote());
        } else {
            saveButton.setOnClickListener(view1 -> saveNote());
        }

    }

    void updateNote() {

        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, titleNote.getText().toString());
        bundle.putString(ARG_INFO, infoNote.getText().toString());

        getParentFragmentManager().setFragmentResult(KEY_RESULT, bundle);

        UserNotesRepository.getInstance().update(UUID.randomUUID().toString(), titleNote.getText().toString(), infoNote.getText().toString(), new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                backToList();
            }

            @Override
            public void onError(Throwable error) {

            }
        });

    }

    void saveNote() {
        Note note = new Note(UUID.randomUUID().toString(), titleNote.getText().toString(), infoNote.getText().toString());

        UserNotesRepository.getInstance().addNote(note);

        backToList();
    }

    void backToList() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new NotesListFragment())
                .commit();
    }
}
