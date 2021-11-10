package com.thefear.seconttrymynotes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserNotesRepository implements NotesRepository {

    private static UserNotesRepository mInstance;
    private ArrayList<Note> notes;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.myLooper());

    public static UserNotesRepository getInstance() {
        if (mInstance == null)
            mInstance = new UserNotesRepository();

        return mInstance;
    }

    private UserNotesRepository() {
        notes = new ArrayList<>();
        notes.add(new Note("Заметка 1", "Создайте список ваших заметок."));
        notes.add(new Note("Заметка 2", "Создайте карточку для элемента списка."));
        notes.add(new Note("Заметка 3", "Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка."));
        notes.add(new Note("Заметка 4", "Создайте фрагмент для редактирования данных в конкретной карточке. Этот фрагмент пока можно вызвать через основное меню."));
        notes.add(new Note("Заметка 5", "RecyclerView — список, который умеет переиспользовать элементы."));
        notes.add(new Note("Заметка 6", "Карточка (CardView) — элемент списка."));
        notes.add(new Note("Заметка 7", "Источник данных — класс, формирующий и подготавливающий данные."));
        notes.add(new Note("Заметка 8", "Сделайте фрагмент добавления и редактирования данных, если вы ещё не сделали его."));
        notes.add(new Note("Заметка 9", "Сделайте навигацию между фрагментами, также организуйте обмен данными между фрагментами."));
        notes.add(new Note("Заметка 10", "Создайте контекстное меню для изменения и удаления заметок."));
    }

    @Override
    public void getNotes(Callback<ArrayList<Note>> callback) {
        executor.execute(() -> mainThreadHandler.post(() -> callback.onSuccess(notes)));
    }

    public void addNote(Note note) {
        notes.add(note);
    }

}