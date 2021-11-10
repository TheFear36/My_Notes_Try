package com.thefear.seconttrymynotes.domain;

import java.util.ArrayList;

public interface NotesRepository {
    void getNotes(Callback<ArrayList<Note>> notes);
}
