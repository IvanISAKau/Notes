package com.example.notes.ui.list;

import com.example.notes.domain.Note;

import java.util.List;

public interface ListView {

    void showNotes(List<Note> notes);

    void addNote(Note note);

    void removeNote(Note note, int index);

    void showProgress();

    void hideProgress();

}
