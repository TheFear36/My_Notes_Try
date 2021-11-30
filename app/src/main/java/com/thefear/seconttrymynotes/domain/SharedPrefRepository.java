package com.thefear.seconttrymynotes.domain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefRepository implements NotesRepository {

    private static final String ARG_NOTES_LIST = "ARG_NOTES_LIST";

    private SharedPreferences sharedPreferences;

    private final Gson gson = new Gson();

    private final Type type = new TypeToken<ArrayList<Note>>() {
    }.getType();

    public SharedPrefRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPrefRepository", Context.MODE_PRIVATE);
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        String data = sharedPreferences.getString(ARG_NOTES_LIST, "[]");

        ArrayList<Note> notes = gson.fromJson(data, type);

        callback.onSuccess(notes);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void delete(Note note, Callback<Void> callback) {
        String data = sharedPreferences.getString(ARG_NOTES_LIST, "[]");
        ArrayList<Note> notes = gson.fromJson(data, type);

        notes.remove(note);
        sharedPreferences.edit().putString(ARG_NOTES_LIST, gson.toJson(notes, type)).apply();
        callback.onSuccess(null);
    }

    @Override
    public void update(String id, String title, String info, Callback<Note> callback) {

        String data = sharedPreferences.getString(ARG_NOTES_LIST, "[]");
        ArrayList<Note> notes = gson.fromJson(data, type);

        Note note = new Note(id, title, info);
        int index = -1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            notes.set(index, note);
        }


        sharedPreferences.edit().putString(ARG_NOTES_LIST, gson.toJson(notes, type)).apply();

        callback.onSuccess(note);

    }

    @Override
    public void addNote(Note note, Callback<Note> callback) {
        String data = sharedPreferences.getString(ARG_NOTES_LIST, "[]");
        ArrayList<Note> notes = gson.fromJson(data, type);

        notes.add(note);

        sharedPreferences.edit().putString(ARG_NOTES_LIST, gson.toJson(notes, type)).apply();

        callback.onSuccess(note);

    }

}
