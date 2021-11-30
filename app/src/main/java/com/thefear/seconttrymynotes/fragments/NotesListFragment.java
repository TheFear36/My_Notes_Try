package com.thefear.seconttrymynotes.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
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
import com.thefear.seconttrymynotes.domain.SharedPrefRepository;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {
    private RecyclerView notesContainer;

    private NoteListPresenter presenter;

    private NotesAdapter adapter;

    private ProgressBar progressBar;

    private Note selectedNote;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new NotesAdapter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesContainer = view.findViewById(R.id.notes_container);

        notesContainer.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress);
        presenter = new NoteListPresenter(this, new SharedPrefRepository(requireActivity().getApplicationContext()));
        presenter.requestNotes();

        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                selectedNote = note;
                clickOnItemView();
            }

            @Override
            public void onNoteLongClicked(View view, Note note) {
                longClickOnItemView(view);
                selectedNote = note;
            }
        });

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

        getParentFragmentManager().setFragmentResultListener(NotesInfoFragment.KEY_RESULT, getViewLifecycleOwner(), (requestKey, result) -> {
            String title = result.getString(NotesInfoFragment.ARG_TITLE);
            String info = result.getString(NotesInfoFragment.ARG_INFO);

            presenter.updateNote(title, info, selectedNote);
        });
        getParentFragmentManager().setFragmentResultListener(NotesInfoFragment.KEY_RESULT_ADD, getViewLifecycleOwner(), (requestKey, result) -> {
            String id = result.getString(NotesInfoFragment.ARG_ID);
            String title = result.getString(NotesInfoFragment.ARG_TITLE);
            String info = result.getString(NotesInfoFragment.ARG_INFO);

            Note newNote = new Note(id, title, info);

            presenter.add(newNote);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showNotes(List<Note> notes) {
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

    @Override
    public void deleteNote(Note selectedNote) {
        int position = adapter.deleteNote(selectedNote);

        adapter.notifyItemRemoved(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateNote(Note result) {
        adapter.updateNote(result);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addNote(Note result) {
        adapter.add(result);
        adapter.notifyDataSetChanged();
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
                .replace(R.id.fragment_container, NotesInfoFragment.newInstance(selectedNote))
                .addToBackStack(null)
                .commit();
    }

    private void longClickOnItemView(View v) {
        v.showContextMenu();

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_update) {

            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            presenter.delete(selectedNote);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
