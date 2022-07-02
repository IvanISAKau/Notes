package com.example.notes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesRepositoryImpl implements NotesRepository {

    private static ArrayList<Note> notes;

    private static NotesRepositoryImpl instance;

    private NotesRepositoryImpl() {
    }

    public static NotesRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new NotesRepositoryImpl();

            notes = new ArrayList<>();
            notes.add(new Note(UUID.randomUUID().toString(), "Починить тостер", "Купить новый ядерный реактор на барахолке и заменить"));
            notes.add(new Note(UUID.randomUUID().toString(), "Купить билеты в кино", "8 билетов на вечерний сеанс в 3-м ряду"));
            notes.add(new Note(UUID.randomUUID().toString(), "Выкинуть мусор", "Отсортировать пластик, бумагу и стекло отдельно от бытовых отходов"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 1", "Текст заметки 1"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 2", "Текст заметки 2"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 3", "Текст заметки 3"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 4", "Текст заметки 4"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 5", "Текст заметки 5"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 6", "Текст заметки 6"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 7", "Текст заметки 7"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 8", "Текст заметки 8"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 9", "Текст заметки 9"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 10", "Текст заметки 10"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 11", "Текст заметки 11"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 12", "Текст заметки 12"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 13", "Текст заметки 13"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 14", "Текст заметки 14"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 15", "Текст заметки 15"));
            notes.add(new Note(UUID.randomUUID().toString(), "Заметка 16", "Текст заметки 16"));
        }
        return instance;
    }

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void addNote() {
        // todo
    }

    @Override
    public void deleteNote(Note note) {
        notes.remove(note);
    }

    @Override
    public void changeTitle() {
        // todo
    }

    @Override
    public void changeNoteBody() {
        // todo
    }

    @Override
    public void changeDate(Note note, Date newDate) {
        note.setDate(newDate);
    }

}
