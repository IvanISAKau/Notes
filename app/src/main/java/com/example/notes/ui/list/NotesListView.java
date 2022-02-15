package com.example.notes.ui.list;

import com.example.notes.domain.Note;

import java.util.List;

public interface NotesListView {

    void showNotes(List<Note> notes);
}
