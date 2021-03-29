package com.example.stopnjot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    List<Note> mNoteList = new ArrayList<>();
    Context mContext;

    public NotesAdapter(List<Note> noteList, Context context) {
        this.mNoteList = noteList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.message.setText(mNoteList.get(position).getMessage());
        holder.date.setText(mNoteList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView message, date;
        RelativeLayout noteRelLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.message = itemView.findViewById(R.id.msgTextView);
            this.date = itemView.findViewById(R.id.dateTextView);
            this.noteRelLayout = itemView.findViewById(R.id.noteView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note noteData = mNoteList.get(getAdapterPosition());

                    Intent jotNoteIntent = new Intent(mContext, EditNoteActivity.class);

                    jotNoteIntent.putExtra("id", noteData.getId());
                    jotNoteIntent.putExtra("message", noteData.getMessage());
                    jotNoteIntent.putExtra("date", noteData.getDate());

                    mContext.startActivity(jotNoteIntent);
                }
            });
        }
    }
}