package com.example.notes.domain;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

//    List<Note> getNotes(); // синхронный метод

    void getNotes(Callback<List<Note>> callback);

    void addNote(String title, String content, Callback<Note> callback);

    void deleteNote(Note note, Callback<Void> callback);

    void changeTitle();

    void changeNoteBody();

    Note updateNote(String id, String newTitle, String newContent);

    void changeDate(Note note, Date newDate);
}
