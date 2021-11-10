package com.thefear.seconttrymynotes.Presenter;

import com.thefear.seconttrymynotes.domain.Callback;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.UserNotesRepository;

import java.util.ArrayList;

public class NoteListPresenter {
    private final NotesListView view;
    private final UserNotesRepository repository;

    public NoteListPresenter(NotesListView view, UserNotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes(){

        view.showProgress();

        repository.getNotes(new Callback<ArrayList<Note>>() {
            @Override
            public void onSuccess(ArrayList<Note> result) {
                view.showNotes(result);

                view.hideProgress();
            }
            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
