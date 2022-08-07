package com.example.notes.domain;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

    List<Note> getNotes();

    Note addNote(String title, String content);

    void deleteNote(Note note);

    void changeTitle();

    void changeNoteBody();

    void changeDate(Note note, Date newDate);
}
