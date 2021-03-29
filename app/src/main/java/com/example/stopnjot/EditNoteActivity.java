package com.example.stopnjot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNoteActivity extends AppCompatActivity {
    EditText noteEditText;
    Button cancelButton, updateButton, deleteButton;
    String noteMsg = "", noteDate = "";
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);

        Bundle bundle = getIntent().getExtras();

        final String noteId = bundle.getString("id");
        noteMsg = bundle.getString("message");
        noteDate = bundle.getString("date");

        noteEditText = findViewById(R.id.editMsgNote);
        cancelButton = findViewById(R.id.cancelEditNoteButton);
        updateButton = findViewById(R.id.updateEditNoteButton);
        deleteButton = findViewById(R.id.deleteEditNoteButton);

        noteEditText.setText(noteMsg);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        String dbId = mDatabaseReference.getKey();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(noteId);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(noteId);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEditing();
            }
        });
    }

    private void updateNote(String id) {
        noteMsg = noteEditText.getText().toString();

        Note editNoteList = new Note(id, noteMsg, noteDate);

        mDatabaseReference.child("Notes").child(id).setValue(editNoteList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditNoteActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NotesActivity.class));
            }
        });
    }

    private void deleteNote(String id) {
        mDatabaseReference.child("Notes").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditNoteActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NotesActivity.class));
            }
        });
    }

    private void cancelEditing() {
        Toast.makeText(EditNoteActivity.this, "No change made", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), NotesActivity.class));
    }
}
