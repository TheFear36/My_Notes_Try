package com.thefear.seconttrymynotes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.thefear.seconttrymynotes.Presenter.NoteListPresenter;
import com.thefear.seconttrymynotes.Presenter.NotesListView;
import com.thefear.seconttrymynotes.R;
import com.thefear.seconttrymynotes.contracts.ToolbarForActivity;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.UserNotesRepository;

import java.util.ArrayList;

public class NotesListFragment extends Fragment implements NotesListView {
    private LinearLayout notesContainer;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesContainer = view.findViewById(R.id.notes_container);

        NoteListPresenter presenter = new NoteListPresenter(this, UserNotesRepository.getInstance());
        presenter.requestNotes();

        Toolbar toolbar = view.findViewById(R.id.list_toolbar);

        if (getActivity() instanceof ToolbarForActivity) {
            ToolbarForActivity drawer = (ToolbarForActivity) getActivity();
            drawer.setToolbar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    createNewNote();
                    return true;
                } else if (item.getItemId() == R.id.action_settings) {
                    openSettings();
                    return true;
                } else if (item.getItemId() == R.id.action_share) {
                    Toast.makeText(requireContext(), "Share pressed", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showNotes(ArrayList<Note> notes) {
        for (Note note : notes) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, notesContainer, false);
            TextView nameNote = itemView.findViewById(R.id.title_note);
            nameNote.setText(note.getTitle());

            notesContainer.addView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickOnItemView();
                }
            });
        }
    }

    void createNewNote() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new NotesInfoFragment())
                .addToBackStack(null)
                .commit();
    }

    void openSettings() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    void clickOnItemView() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new NotesInfoFragment())
                .addToBackStack(null)
                .commit();
    }
}
