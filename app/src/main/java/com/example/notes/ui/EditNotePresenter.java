package com.example.notes.ui;

import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepository;

public class EditNotePresenter extends AbstractNotePresenter{

    public static final String KEY_EDIT = "EditNotePresenter";

    private Note toEdit;

    public EditNotePresenter(EditNoteView view, NotesRepository repository, Note note) {
        super(view, repository);
        toEdit = note;

        title = toEdit.getTitle();
        content = toEdit.getNoteBody();

    }

    @Override
    void refresh() {
        view.setButtonTitle(R.string.btn_update);
        view.setNoteTitle(title);
        view.setNoteDescription(content);
    }

    @Override
    void onActionButtonClicked() {

        view.showProgress();

        repository.updateNote(toEdit.getId(), title, content, new Callback<Note>() {
            @Override
            public void onSuccess(Note data) {

                Bundle bundle = new Bundle();
                bundle.putParcelable(EditNoteBottomSheetDialogFragment.ARG_NOTE, data);

                view.publishResult(KEY_EDIT, bundle);

                view.hideProgress();
            }
        });

    }
}
