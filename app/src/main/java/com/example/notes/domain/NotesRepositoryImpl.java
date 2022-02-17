package com.example.notes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesRepositoryImpl implements NotesRepository {

    private static NotesRepositoryImpl instance;
    private NotesRepositoryImpl() {}
    public static NotesRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new NotesRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Note> getNotes() {
        // todo:в дальнейшем заменить затычку на реальный функционал
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(UUID.randomUUID().toString(), "Починить тостер", "Купить новый ядерный реактор на барахолке и заменить"));
        notes.add(new Note(UUID.randomUUID().toString(), "Купить билеты в кино", "8 билетов на вечерний сеанс в 3-м ряду"));
        notes.add(new Note(UUID.randomUUID().toString(), "Выкинуть мусор", "Отсортировать пластик, бумагу и стекло отдельно от бытовых отходов"));

        return notes;
    }

    @Override
    public void addNote() {
        // todo
    }

    @Override
    public void deleteNote() {
        // todo
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
        // todo
        note.setDate(newDate);
    }
}
