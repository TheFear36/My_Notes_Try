package com.thefear.seconttrymynotes.Presenter;

import com.thefear.seconttrymynotes.domain.Note;

import java.util.ArrayList;

public interface NotesListView {
    void showNotes(ArrayList<Note> notes);
}
