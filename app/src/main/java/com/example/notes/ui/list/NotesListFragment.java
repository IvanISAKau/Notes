package com.example.notes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notes.R;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepositoryImpl;
import com.example.notes.ui.EditNoteBottomSheetDialogFragment;
import com.example.notes.ui.NavDrawable;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class NotesListFragment extends Fragment implements ListView{

    public static final String NOTE_SELECTED = "NOTE_SELECTED";
    public static final String SELECTED_NOTE_BUNDLE = "SELECTED_NOTE_BUNDLE";
    public static final String LIST_TAG = "LIST_TAG";

    //private LinearLayout container;

    private RecyclerView list;

//    private NotesRepositoryImpl repository;
    private NotesListPresenter presenter;

    private NotesListAdapter adapter;

//    private Note selectedNote;
//    private int selectedNoteIndex;

    private ProgressBar progressBar;

    //private NotesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //repository = NotesRepositoryImpl.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new NotesListPresenter(NotesRepositoryImpl.getInstance(), this);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        progressBar = view.findViewById(R.id.progress);

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

        list = view.findViewById(R.id.list_container);
        //list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        //list.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        list.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        adapter = new NotesListAdapter(this);

        list.setAdapter(adapter);

        adapter.setOnNoteClicked(new NotesListAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {

                Bundle bundle = new Bundle();
                bundle.putParcelable(SELECTED_NOTE_BUNDLE, note);

                getParentFragmentManager()
                        .setFragmentResult(NOTE_SELECTED, bundle);
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {
                presenter.setSelectedNote(note);
                presenter.setSelectedNoteIndex(position);

//                selectedNote = note;
//                selectedNoteIndex = position;
            }
        });

////        adapter.setData(repository.getNotes()); // это все синхронно!
////
////        adapter.notifyDataSetChanged();
//
//        // а так асинхронно:
//        repository.getNotes(new Callback<List<Note>>() {
//            @Override
//            public void onSuccess(List<Note> data) {
////                adapter.setData(data);
////                adapter.notifyDataSetChanged();
//            }
//        });

        view.findViewById(R.id.add_floating_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Note note = repository.addNote("Добавленная заметка", "Контент добавленной заметки по add_floating_button");

                presenter.addItem();

//                int index = adapter.addItem(note);
//                adapter.notifyItemInserted(index);
//
//                list.smoothScrollToPosition(index);
            }
        });

        getParentFragmentManager().setFragmentResultListener(EditNoteBottomSheetDialogFragment.REQUEST_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(EditNoteBottomSheetDialogFragment.ARG_EDIT_NOTE);

                adapter.updateItem(note, presenter.getSelectedNoteIndex());

                adapter.notifyItemChanged(presenter.getSelectedNoteIndex());
            }
        });

        presenter.requestNotes();

        //container = view.findViewById(R.id.container);

        //presenter.requestNotes();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_note:

                EditNoteBottomSheetDialogFragment.newInstance(presenter.getSelectedNote())
                        .show(getParentFragmentManager(), "EditNoteBottomSheetDialogFragment");
                return true;
            case R.id.action_delete_note:

                presenter.deleteItem();

//                repository.deleteNote(selectedNote);
//
//                adapter.removeItem(selectedNoteIndex);
//
//                adapter.notifyItemRemoved(selectedNoteIndex);

                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void showNotes(List<Note> notes) {

        adapter.setData(notes);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void addNote(Note note) {

        int index = adapter.addItem(note);
        adapter.notifyItemInserted(index);

        list.smoothScrollToPosition(index);

    }

    @Override
    public void removeNote(Note note, int index) {

        adapter.removeItem(index);

        adapter.notifyItemRemoved(presenter.getSelectedNoteIndex());

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
