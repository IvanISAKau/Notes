package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepository;
import com.example.notes.domain.NotesRepositoryImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static final String ARG_EDIT_NOTE = "ARG_EDIT_NOTE";
    public static final String REQUEST_KEY = "EditNoteBottomSheetDialogFragment_REQUEST_KEY";


    public static EditNoteBottomSheetDialogFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_EDIT_NOTE, note);

        EditNoteBottomSheetDialogFragment fragment = new EditNoteBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note_bottom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = requireArguments().getParcelable(ARG_EDIT_NOTE);

        EditText title = view.findViewById(R.id.edit_title);
        title.setText(note.getTitle());
        EditText content = view.findViewById(R.id.edit_content);
        content.setText(note.getNoteBody());

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Note updatedNote = NotesRepositoryImpl.getInstance().updateNote(note.getId(), title.getText().toString(), content.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_EDIT_NOTE, updatedNote);

                getParentFragmentManager()
                        .setFragmentResult(REQUEST_KEY, bundle);

                dismiss();
            }
        });

    }
}
