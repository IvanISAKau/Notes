package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepositoryImpl implements NotesRepository {

    private static ArrayList<Note> notes;

    private static InMemoryNotesRepositoryImpl instance;

    private Executor executor = Executors.newSingleThreadExecutor();

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private InMemoryNotesRepositoryImpl() {
    }

    public static InMemoryNotesRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InMemoryNotesRepositoryImpl();

            notes = new ArrayList<>();
            notes.add(new Note(UUID.randomUUID().toString(), "Починить тостер", "Купить новый ядерный реактор на барахолке и заменить"));
            notes.add(new Note(UUID.randomUUID().toString(), "Купить билеты в кино", "8 билетов на вечерний сеанс в 3-м ряду"));
            notes.add(new Note(UUID.randomUUID().toString(), "Выкинуть мусор", "Отсортировать пластик, бумагу и стекло отдельно от бытовых отходов"));
            notes.add(new Note(UUID.randomUUID().toString(), "Очень длинный текст :)", "Предисловие: Наконец появилась возможность добраться до интернета, сейчас мы находимся в Панамском канале и здесь есть wifi. Я на судне уже больше месяца и пока я здесь, я писал все интересное что здесь происходит и вот наконец есть возможность этим поделиться. Фотографий пока не будет, их я выложу или позже, или уже когда вернусь домой. Итак, понеслась:\n" +
                    "\n" +
                    "\n" +
                    "Первые впечатления\n" +
                    "\n" +
                    "Переночевав в гостинице в Гуаякиле, мы сели к агенту в машину и поехали на судно в Пуэрто Боливар. Доехали вопреки ожиданиям быстро, примерно за 3-4 часа. Погода была пасмурная и даже не смотря на то, что мы находимся недалеко от экватора, было прохладно. Почти все время, пока мы ехали, по обе стороны дороги были банановые плантации, но все равно в голове не укладывается: эти бананы грузят на суда в нескольких портах Эквадора десятками тысяч тонн каждый день, круглый год. Это ж несчастные бананы должны расти быстрее чем грибы.\n" +
                    "\n" +
                    "Дороги.\n" +
                    "Дороги в Эквадоре практически идеальные, хотя населенные пункты выглядят очень бедно. На дорогах много интересных машин, например очень много грузовиков - древних Фордов, которые я никогда раньше не видел. А еще несколько раз на глаза попадались старенькие Жигули :) А еще если кого-то обгоняешь и есть встречная машина, она обязательно включает фары. На больших машинах - грузовиках и автобусах, обязательно красуется местный тюнинг: машины разукрашенные, либо в наклейках, и обязательно везде огромное множество светодиодов, как будто новогодние елки едут и переливаются всеми цветами.\n"));
            notes.add(new Note(UUID.randomUUID().toString(), "Работа", "Сразу, как только мы заселились, я не успел разложить вещи, как в мою голову ворвался такой поток информации, что ни в сказке сказать, ни топором не вырубить. Во-первых, на судне абсолютно все бумаги - мануалы, журналы, и так далее - все на английском языке. Даже блокнотик, в который записываются отчеты по грузовым операциям - и тот на английском. Бумаги... ооооо... Их тысячи, лежат в сотнях папок, плюс огромное количество документов на компьютерах. Это мне просто разорвало мозг в клочья, потому что с этим объемом информации надо ознакомиться и научиться работать в кротчайшие сроки. Постоянная беготня, постоянная суета, совсем не легко. А также надо как можно быстрее разобраться со всем оборудованием на мостике, а там его мама не горюй. В общем, пока что, свободного времени нет вообще. Абсолютно. Только ночью с 00:00 до 06:00 можно поспать. Но это продлится не долго, буквально 1-2 недели, потом океанский переход до Европы, можно будет уже спокойно стоять вахты, а в свободное время читать книги компании Seatrade, на случай если в Европе придет проверка и будет задавать вопросы."));

            for (int i = 1; i < 50; i++) {
                notes.add(new Note(UUID.randomUUID().toString(), "Заметка " + i, "Текст заметки " + i));
            }
        }
        return instance;
    }

//    @Override
//    public List<Note> getNotes() {
//        return notes;
//    }

    // асинхронно:

    //    @Override
//    public void getNotes(Callback<List<Note>> callback) {
//                callback.onSuccess(notes);
//    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                callback.onSuccess(notes);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(notes);
                    }
                });

            }
        });

    }

    @Override
    public void addNote(String title, String content, Callback<Note> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note note = new Note(UUID.randomUUID().toString(), title, content);
                notes.add(note);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });

            }
        });

    }

    @Override
    public void deleteNote(Note note, Callback<Void> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                notes.remove(note);

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });

            }
        });

    }

//    @Override
//    public Note addNote(String title, String content) {
//        Note note = new Note(UUID.randomUUID().toString(), title, content);
//        notes.add(note);
//        return note;
//    }
//
//    @Override
//    public void deleteNote(Note note) {
//        notes.remove(note);
//    }

    @Override
    public void changeTitle() {
        // todo
    }

    @Override
    public void changeNoteBody() {
        // todo
    }

    @Override
    public void updateNote(String id, String newTitle, String newContent, Callback<Note> callback) {

        Note toChange = null;
        int indexToChange = -1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                toChange = notes.get(i);
                indexToChange = i;
                break;
            }
        }

        Note newNote = new Note(toChange.getId(), newTitle, newContent);

        notes.set(indexToChange, newNote);
        callback.onSuccess(newNote);

    }

    @Override
    public void changeDate(Note note, Date newDate) {
        note.setDate(newDate);
    }

}
