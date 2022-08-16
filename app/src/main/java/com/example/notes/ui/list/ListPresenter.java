package com.example.notes.ui.list;

import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepositoryImpl;

import java.util.List;

class NotesListPresenter {

    private NotesRepositoryImpl repository;
    private ListView view;

    private Note selectedNote;
    private int selectedNoteIndex;

    public NotesListPresenter(NotesRepositoryImpl repository, ListView view) {
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

    public void addItem() {
        view.showProgress();

        repository.addNote("Добавленная заметка", "Добавленный контент...........", new Callback<Note>() {
            @Override
            public void onSuccess(Note data) {

                view.hideProgress();
                view.addNote(data);
            }
        });
        
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
