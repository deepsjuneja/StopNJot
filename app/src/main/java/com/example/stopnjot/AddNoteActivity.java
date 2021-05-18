package com.example.stopnjot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    EditText noteMsgText;
    Button saveNoteButton, cancelAddNoteButton;
    String noteMsg, addNoteDate;
    private DatabaseReference databaseReference;
    private FirebaseUser currUser;
    private String uId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        noteMsgText = findViewById(R.id.msgNote);

        databaseReference = FirebaseDatabase.getInstance().getReference("notes");
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        uId = currUser.getUid();

        saveNoteButton = findViewById(R.id.saveNoteButton);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote(v);
            }
        });

        cancelAddNoteButton = findViewById(R.id.cancelAddNoteButton);
        cancelAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOperation();
            }
        });
    }

    public void addNote(View view) {
        noteMsg = noteMsgText.getText().toString();

        if(TextUtils.isEmpty(noteMsg))
            return;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy, HH:mm");
        addNoteDate = simpleDateFormat.format(calendar.getTime());

        addNote(noteMsg, addNoteDate);
    }

    private void addNote(String noteMessage, String noteDate) {
        String id = databaseReference.child(uId).push().getKey();
        Note noteList = new Note(id, noteMessage, noteDate);

        databaseReference.child(uId).child(id).setValue(noteList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddNoteActivity.this, "Note added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NotesActivity.class));
            }
        });
    }

    private void cancelOperation() {
        startActivity(new Intent(getApplicationContext(), NotesActivity.class));
    }
}
