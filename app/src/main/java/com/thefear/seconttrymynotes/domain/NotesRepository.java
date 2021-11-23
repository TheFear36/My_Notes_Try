package com.thefear.seconttrymynotes.domain;

import java.util.List;

public interface NotesRepository {
    void getNotes(Callback<List<Note>> notes);

    void delete(Note note, Callback<Void> callback);

    void update(String id, String title, String info, Callback<Note> callback);
}
