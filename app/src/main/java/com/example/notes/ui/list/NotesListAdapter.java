package com.example.notes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    public OnNoteClicked getOnNoteClicked() {
        return onNoteClicked;
    }

    public void setOnNoteClicked(OnNoteClicked onNoteClicked) {
        this.onNoteClicked = onNoteClicked;
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    private List<Note> data = new ArrayList<>();

    public void setData(Collection<Note> toSet) {
        data.clear();
        data.addAll(toSet);
    }

    private OnNoteClicked onNoteClicked;

    // переопредилить если несколько разных типов
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note item = data.get(position);

        holder.noteTitle.setText(item.getTitle());
        holder.noteContent.setText(item.getNoteBody());
        holder.date.setText(format.format(item.getDate()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;
        TextView noteContent;
        TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.item_card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnNoteClicked() != null) {

                        int clickedPosition = getAdapterPosition();

                        getOnNoteClicked().onNoteClicked(data.get(clickedPosition));
                    }
                }
            });

            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
        }
    }
}
