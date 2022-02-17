package com.example.notes.domain;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

    List<Note> getNotes();

    void addNote();

    void deleteNote();

    void changeTitle();

    void changeNoteBody();

    void changeDate(Note note, Date newDate);
}
