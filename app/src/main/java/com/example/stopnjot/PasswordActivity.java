package com.example.stopnjot;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PasswordActivity extends AppCompatActivity {
    private static RecyclerView recyclerView2;
    PasswordAdapter passwordAdapter;
    List<Password> passwordList = new ArrayList<>();
    TextView no_password_view;
    FloatingActionButton fab2;
    FirebaseDatabase firebaseDatabase2;
    private FirebaseUser currUser;
    private DatabaseReference databaseReference2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        no_password_view = findViewById(R.id.no_passwords);
        fab2 = findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generatePasswordIntent = new Intent(PasswordActivity.this, GeneratePasswordActivity.class);
                startActivity(generatePasswordIntent);
            }
        });

        passwordAdapter = new PasswordAdapter(passwordList, this);
        recyclerView2.setAdapter(passwordAdapter);

        currUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase2 = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase2.getReference("passwords");
        String uId = currUser.getUid();
        databaseReference2.child(uId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Password newPassword = dataSnapshot.getValue(Password.class);
                    passwordList.add(newPassword);
                }
                passwordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(PasswordActivity.this, UserActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
