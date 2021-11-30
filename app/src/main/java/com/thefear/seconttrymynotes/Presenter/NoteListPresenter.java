package com.thefear.seconttrymynotes.Presenter;

import com.thefear.seconttrymynotes.domain.Callback;
import com.thefear.seconttrymynotes.domain.Note;
import com.thefear.seconttrymynotes.domain.NotesRepository;

import java.util.List;

public class NoteListPresenter {
    private final NotesListView view;
    private final NotesRepository repository;

    public NoteListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {

        view.showProgress();

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                view.showNotes(result);

                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void delete(Note selectedNote) {
        view.showProgress();
        repository.delete(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.deleteNote(selectedNote);
                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void updateNote(String title, String info, Note selectedNote) {
        view.showProgress();
        repository.update(selectedNote.getId(), title, info, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.updateNote(result);
                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void add(Note note) {
        view.showProgress();
        repository.addNote(note, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.addNote(result);
                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
