package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepositoryImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

public class MyBottomSheetDeleteDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    public static MyBottomSheetDeleteDialogFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        MyBottomSheetDeleteDialogFragment fragment = new MyBottomSheetDeleteDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_delete_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cancel_btn_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.delete_btn_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NotesRepositoryImpl.getInstance().deleteNote(getArguments().getParcelable(ARG_NOTE));
                getParentFragmentManager().popBackStack();
                Toast.makeText(requireContext(), "Note has been deleted", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}
