package com.example.notes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepositoryImpl;
import com.example.notes.ui.NavDrawable;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String NOTE_SELECTED = "NOTE_SELECTED";
    public static final String SELECTED_NOTE_BUNDLE = "SELECTED_NOTE_BUNDLE";
    public static final String LIST_TAG = "LIST_TAG";

    private LinearLayout container;

    private NotesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, NotesRepositoryImpl.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if (requireActivity() instanceof NavDrawable) {
            ((NavDrawable) requireActivity()).initDrawer(toolbar);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.action_add:
                        Toast.makeText(requireContext(), "New note has been added", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_search:
                        Toast.makeText(requireContext(), "Searching...", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });

        container = view.findViewById(R.id.container);

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {

        for (Note note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(SELECTED_NOTE_BUNDLE, note);

                    getParentFragmentManager()
                            .setFragmentResult(NOTE_SELECTED, bundle);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    PopupMenu popupMenu = new PopupMenu(requireContext(), view);
                    requireActivity().getMenuInflater().inflate(R.menu.menu_pop_list, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getItemId() == R.id.action_delete_note) {
                                // todo
                                Toast.makeText(requireContext(), "Note has been deleted", Toast.LENGTH_SHORT).show();
                            }

                            return false;
                        }
                    });

                    popupMenu.show();

                    return false;
                }
            });

            TextView noteTitle = itemView.findViewById(R.id.note_title);
            noteTitle.setText(note.getTitle());

            container.addView(itemView);
        }

    }
}
