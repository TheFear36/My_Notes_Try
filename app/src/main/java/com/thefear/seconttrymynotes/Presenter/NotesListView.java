package com.thefear.seconttrymynotes.Presenter;

import com.thefear.seconttrymynotes.domain.Note;

import java.util.List;

public interface NotesListView {
    void showNotes(List<Note> notes);

    void showProgress();

    void hideProgress();

    void deleteNote(Note selectedNote);

    void updateNote(Note result);
}
