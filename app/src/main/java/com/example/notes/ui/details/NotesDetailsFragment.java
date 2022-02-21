package com.example.notes.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepositoryImpl;
import com.example.notes.ui.MainActivity;
import com.example.notes.ui.NavDrawable;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesDetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private static final String DATE_PICKER = "DATE_PICKER";
    private static final String TIME_PICKER = "TIME_PICKER";

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar_details);

        if (requireActivity() instanceof NavDrawable) {
            ((NavDrawable) requireActivity()).initDrawer(toolbar);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.action_share:
                        Toast.makeText(requireContext(), "Note has been shared", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_add_an_image:
                        Toast.makeText(requireContext(), "Image has been added", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_delete:
                        Toast.makeText(requireContext(), "Note has been deleted", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });

        title = view.findViewById(R.id.details_note_title);
        date = view.findViewById(R.id.date);
        noteBody = view.findViewById(R.id.note_body);

        updateNote(getCurrentNote());

        view.findViewById(R.id.date_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDatePicker(getCurrentNote());
            }
        });

    }

    private Note getCurrentNote() {

        Note note = null;
        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(ARG_NOTE)) {

            note = arguments.getParcelable(ARG_NOTE);
        }
        return note;
    }

    private void startDatePicker(Note note) {

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setSelection(note.getDate().getTime());
        MaterialDatePicker<Long> datePicker = builder.build();

        datePicker.show(getParentFragmentManager(), DATE_PICKER);

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(23)
                        .setMinute(60)
                        .build();

                timePicker.show(getParentFragmentManager(), TIME_PICKER);

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String date = new SimpleDateFormat("dd.MM.yyyy").format(datePicker.getSelection())
                                + " " + timePicker.getHour() + ":" + timePicker.getMinute();

                        Date newDate = new Date();

                        try {
                            newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        NotesRepositoryImpl.getInstance().changeDate(note, newDate);

                        updateNote(getCurrentNote());
                    }
                });

            }
        });
    }

    private void updateNote(Note note) {

        title.setText(note.getTitle());

        date.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(note.getDate()));

        noteBody.setText(note.getNoteBody());
    }
}
