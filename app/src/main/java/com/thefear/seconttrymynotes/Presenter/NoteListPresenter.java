package com.thefear.seconttrymynotes.Presenter;

import com.thefear.seconttrymynotes.domain.UserNotesRepository;

import java.util.ArrayList;

public class NoteListPresenter {
    private NotesListView view;
    private UserNotesRepository repository;

    public NoteListPresenter(NotesListView view, UserNotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes(){
        view.showNotes(repository.getNotes());
    }
}
