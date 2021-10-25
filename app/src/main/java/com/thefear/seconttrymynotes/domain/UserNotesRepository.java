package com.thefear.seconttrymynotes.domain;

import java.util.ArrayList;

public class UserNotesRepository implements NotesRepository {

    private static UserNotesRepository mInstance;
    private ArrayList<Note> notes = null;

    public static UserNotesRepository getInstance() {
        if(mInstance == null)
            mInstance = new UserNotesRepository();

        return mInstance;
    }

    private UserNotesRepository() {
        notes = new ArrayList<Note>();
    }
    @Override
    public ArrayList<Note> getNotes() {
        return this.notes;
    }
    public void addNote(Note note) {
        notes.add(note);
    }

}