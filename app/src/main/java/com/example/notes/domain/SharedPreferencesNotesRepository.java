package com.example.notes.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SharedPreferencesNotesRepository implements NotesRepository {

    private static final String KEY_NOTES = "KEY_NOTES";

    private static NotesRepository INSTANCE;

    private List<Note> data = new ArrayList<>();


    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesNotesRepository(context);
        }
        return INSTANCE;
    }

    private final SharedPreferences sharedPreferences;

    private Gson gson = new Gson();

    // getApplicationContext() на всякий случай
    public SharedPreferencesNotesRepository(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences("NotesPreferences", Context.MODE_PRIVATE);

        //костыль, чтобы превратить список заметок в то, что может сохранить sharedPreferences:
        String value = sharedPreferences.getString(KEY_NOTES, "[]");

        Type notesTypes = new TypeToken<List<Note>>() {
        }.getType();

        data.addAll(gson.fromJson(value, notesTypes));

    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        callback.onSuccess(data);
    }

    @Override
    public void addNote(String title, String content, Callback<Note> callback) {

        Note addedNote = new Note(UUID.randomUUID().toString(), title, content);

        Type notesTypes = new TypeToken<List<Note>>() {
        }.getType();

        data.add(addedNote);

        sharedPreferences.edit()
                .putString(KEY_NOTES, gson.toJson(data, notesTypes))
                .apply();

        callback.onSuccess(addedNote);

    }

    @Override
    public void deleteNote(Note note, Callback<Void> callback) {

    }

    @Override
    public void changeTitle() {

    }

    @Override
    public void changeNoteBody() {

    }

    @Override
    public void updateNote(String id, String newTitle, String newContent, Callback<Note> callback) {

    }

    @Override
    public void changeDate(Note note, Date newDate) {

    }
}
