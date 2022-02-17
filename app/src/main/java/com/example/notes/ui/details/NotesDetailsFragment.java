package com.example.notes.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.list.NotesListFragment;

import java.text.SimpleDateFormat;

public class NotesDetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    private TextView title;
    private TextView date;
    private TextView noteBody;

    public static NotesDetailsFragment newInstance(Note note) {

        Bundle args = new Bundle();

        args.putParcelable(ARG_NOTE, note);

        NotesDetailsFragment fragment = new NotesDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NotesDetailsFragment() {
        super(R.layout.fragment_notes_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.details_note_title);
        date = view.findViewById(R.id.date);
        noteBody = view.findViewById(R.id.note_body);

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(ARG_NOTE)) {
            Note note = arguments.getParcelable(ARG_NOTE);

            updateNote(note);
        }

    }

    private void updateNote(Note note) {

        title.setText(note.getTitle());

        date.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(note.getDate()));

        noteBody.setText(note.getNoteBody());
    }
}
