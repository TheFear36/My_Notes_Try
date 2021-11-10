package com.thefear.seconttrymynotes.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thefear.seconttrymynotes.Presenter.NoteListPresenter;
import com.thefear.seconttrymynotes.Presenter.NotesListView;
import com.thefear.seconttrymynotes.R;
import com.thefear.seconttrymynotes.contracts.ToolbarForActivity;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.NotesAdapter;
import com.thefear.seconttrymynotes.domain.UserNotesRepository;

import java.util.ArrayList;

public class NotesListFragment extends Fragment implements NotesListView {
    private RecyclerView notesContainer;

    NoteListPresenter presenter;

    private NotesAdapter adapter;

    private ProgressBar progressBar;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesContainer = view.findViewById(R.id.notes_container);

        notesContainer.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress);

        adapter = new NotesAdapter();

        presenter = new NoteListPresenter(this, UserNotesRepository.getInstance());
        presenter.requestNotes();

        adapter.setNoteClicked(note -> clickOnItemView());

        Toolbar toolbar = view.findViewById(R.id.list_toolbar);

        if (getActivity() instanceof ToolbarForActivity) {
            ToolbarForActivity drawer = (ToolbarForActivity) getActivity();
            drawer.setToolbar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add) {
                createNewNote();
                return true;
            } else if (item.getItemId() == R.id.action_settings) {
                openSettings();
                return true;
            } else if (item.getItemId() == R.id.action_share) {
                Toast.makeText(requireContext(), R.string.share, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showNotes(ArrayList<Note> notes) {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        notesContainer.setLayoutManager(lm);

        adapter.setNotes(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
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
