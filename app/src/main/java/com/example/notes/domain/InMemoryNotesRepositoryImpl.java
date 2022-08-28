package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepositoryImpl implements NotesRepository {

    private static ArrayList<Note> notes;

    private static InMemoryNotesRepositoryImpl instance;

    private Executor executor = Executors.newSingleThreadExecutor();

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private InMemoryNotesRepositoryImpl() {
    }

    public static InMemoryNotesRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InMemoryNotesRepositoryImpl();

            notes = new ArrayList<>();
            notes.add(new Note(UUID.randomUUID().toString(), "Починить тостер", "Купить новый ядерный реактор на барахолке и заменить", new Date()));
            notes.add(new Note(UUID.randomUUID().toString(), "Купить билеты в кино", "8 билетов на вечерний сеанс в 3-м ряду", new Date()));
            notes.add(new Note(UUID.randomUUID().toString(), "Выкинуть мусор", "Отсортировать пластик, бумагу и стекло отдельно от бытовых отходов", new Date()));

            for (int i = 1; i < 50; i++) {
                notes.add(new Note(UUID.randomUUID().toString(), "Заметка " + i, "Текст заметки " + i, new Date()));
            }
        }
        return instance;
    }

//    @Override
//    public List<Note> getNotes() {
//        return notes;
//    }

    // асинхронно:

    //    @Override
//    public void getNotes(Callback<List<Note>> callback) {
//                callback.onSuccess(notes);
//    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                callback.onSuccess(notes);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(notes);
                    }
                });

            }
        });

    }

    @Override
    public void addNote(String title, String content, Callback<Note> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note note = new Note(UUID.randomUUID().toString(), title, content, new Date());
                notes.add(note);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });

            }
        });

    }

    @Override
    public void deleteNote(Note note, Callback<Void> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                notes.remove(note);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });

            }
        });

    }

//    @Override
//    public Note addNote(String title, String content) {
//        Note note = new Note(UUID.randomUUID().toString(), title, content);
//        notes.add(note);
//        return note;
//    }
//
//    @Override
//    public void deleteNote(Note note) {
//        notes.remove(note);
//    }

    @Override
    public void changeTitle() {
        // todo
    }

    @Override
    public void changeNoteBody() {
        // todo
    }

    @Override
    public void updateNote(String id, String newTitle, String newContent, Callback<Note> callback) {

        Note toChange = null;
        int indexToChange = -1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                toChange = notes.get(i);
                indexToChange = i;
                break;
            }
        }

        Note newNote = new Note(toChange.getId(), newTitle, newContent, new Date());

        notes.set(indexToChange, newNote);
        callback.onSuccess(newNote);

    }

    @Override
    public void changeDate(Note note, Date newDate) {
        note.setDate(newDate);
    }

}
