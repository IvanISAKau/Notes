package com.example.notes.ui.list;

import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepository;

import java.util.List;

public class NotesListPresenter {

    private final NotesListView view;
    private final NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        List<Note> notes = repository.getNotes();
        view.showNotes(notes);
    }

}
