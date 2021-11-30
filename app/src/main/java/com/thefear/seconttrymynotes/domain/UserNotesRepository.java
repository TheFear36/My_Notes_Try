package com.thefear.seconttrymynotes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserNotesRepository implements NotesRepository {

    private static UserNotesRepository mInstance;
    private List<Note> data;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.myLooper());

    public static UserNotesRepository getInstance() {
        if (mInstance == null)
            mInstance = new UserNotesRepository();

        return mInstance;
    }

    private UserNotesRepository() {
        data = new ArrayList<>();
        data.add(new Note("1", "Заметка 1", "Создайте список ваших заметок."));
        data.add(new Note("2", "Заметка 2", "Создайте карточку для элемента списка."));
        data.add(new Note("3", "Заметка 3", "Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка."));
        data.add(new Note("4", "Заметка 4", "Создайте фрагмент для редактирования данных в конкретной карточке. Этот фрагмент пока можно вызвать через основное меню."));
        data.add(new Note("5", "Заметка 5", "RecyclerView — список, который умеет переиспользовать элементы."));
        data.add(new Note("6", "Заметка 6", "Карточка (CardView) — элемент списка."));
        data.add(new Note("7", "Заметка 7", "Источник данных — класс, формирующий и подготавливающий данные."));
        data.add(new Note("8", "Заметка 8", "Сделайте фрагмент добавления и редактирования данных, если вы ещё не сделали его."));
        data.add(new Note("9", "Заметка 9", "Сделайте навигацию между фрагментами, также организуйте обмен данными между фрагментами."));
        data.add(new Note("10", "Заметка 10", "Создайте контекстное меню для изменения и удаления заметок."));
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(() -> mainThreadHandler.post(() -> callback.onSuccess(data)));
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        executor.execute(() -> {
            data.remove(note);
            mainThreadHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void update(String id, String title, String info, Callback<Note> callback) {
        executor.execute(() -> {
            Note note = new Note(id, title, info);

            int index = -1;

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                data.set(index, note);

                mainThreadHandler.post(() -> callback.onSuccess(note));
            }
        });
    }

    @Override
    public void addNote(Note note, Callback<Note> callback) {

    }

    public void addNote(Note note) {
        data.add(note);
    }

}