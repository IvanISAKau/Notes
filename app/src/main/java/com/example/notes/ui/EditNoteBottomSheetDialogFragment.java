package com.example.notes.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.domain.FirestoreNotesRepository;
import com.example.notes.domain.Note;
import com.example.notes.domain.SharedPreferencesNotesRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditNoteBottomSheetDialogFragment extends BottomSheetDialogFragment implements EditNoteView {

    public static final String ARG_NOTE = "ARG_EDIT_NOTE";
    public static final String REQUEST_KEY = "EditNoteBottomSheetDialogFragment_REQUEST_KEY";

    public static EditNoteBottomSheetDialogFragment newUpdateInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        EditNoteBottomSheetDialogFragment fragment = new EditNoteBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static EditNoteBottomSheetDialogFragment newAddInstance() {

        EditNoteBottomSheetDialogFragment fragment = new EditNoteBottomSheetDialogFragment();

        return fragment;
    }

    private Button actionButton;
    private EditText title;
    private EditText content;
    private ProgressBar progressBar;

    AbstractNotePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note_bottom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionButton = view.findViewById(R.id.btn_edit_bottom_sheet);
        progressBar = view.findViewById(R.id.progress_edit_note_bottom_sheet);

        title = view.findViewById(R.id.edit_title);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                presenter.onTitleChanged(editable.toString());
            }
        });

        content = view.findViewById(R.id.edit_content);

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                presenter.onContentChanged(editable.toString());
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onActionButtonClicked();
            }
        });

        if (getArguments() !=null && getArguments().containsKey(ARG_NOTE)){
            Note note = requireArguments().getParcelable(ARG_NOTE);
            presenter = new EditNotePresenter(this, FirestoreNotesRepository.INSTANCE, note);
            presenter.refresh();

        } else{
            presenter = new AddNotePresenter(this, FirestoreNotesRepository.INSTANCE);
            presenter.refresh();
        }

    }

    @Override
    public void setButtonTitle(int title) {
        actionButton.setText(title);
    }

    @Override
    public void setNoteTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setNoteDescription(String description) {
        content.setText(description);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonEnabled(boolean isEnabled) {
        actionButton.setEnabled(isEnabled);
    }

    @Override
    public void publishResult(String key, Bundle bundle) {

        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        dismiss();

    }
}
