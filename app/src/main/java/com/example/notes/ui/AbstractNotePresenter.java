package com.example.notes.ui;

import android.text.TextUtils;

import com.example.notes.domain.NotesRepository;

public abstract class AbstractNotePresenter {

    protected EditNoteView view;
    protected NotesRepository repository;

    public AbstractNotePresenter(EditNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    protected String title;
    protected String content;

    public void onTitleChanged(String title){
        this.title = title;
        view.setActionButtonEnabled(!TextUtils.isEmpty(title));
    }

    public void onContentChanged(String content){
        this.content = content;
        view.setActionButtonEnabled(!TextUtils.isEmpty(content) && !TextUtils.isEmpty(title));
    }

    abstract void refresh();

    abstract void onActionButtonClicked();

}
