package com.example.notes.ui.list;

import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepository;
import com.example.notes.domain.SharedPreferencesNotesRepository;

import java.util.List;

class NotesListPresenter {

    private NotesRepository repository;
    private ListView view;

    private Note selectedNote;
    private int selectedNoteIndex;

    public NotesListPresenter(NotesRepository repository, ListView view) {
        this.repository = repository;
        this.view = view;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note selectedNote) {
        this.selectedNote = selectedNote;
    }

    public void setSelectedNoteIndex(int selectedNoteIndex) {
        this.selectedNoteIndex = selectedNoteIndex;
    }

    public int getSelectedNoteIndex() {
        return selectedNoteIndex;
    }

    public void deleteItem() {
        view.showProgress();

        repository.deleteNote(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void data) {

                view.hideProgress();

                view.removeNote(selectedNote, selectedNoteIndex);
            }
        });

    }

    public void requestNotes() {

        view.showProgress();

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {

                view.showNotes(data);

                view.hideProgress();

            }
        });
    }
}