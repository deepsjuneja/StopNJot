package com.example.stopnjot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewOrDeletePassword extends AppCompatActivity {
    TextView accViewTextView, passViewTextView;
    Button cancelPButton, deletePButton;
    String accTag = "", passwrd = "";
    private DatabaseReference mDatabaseReference2;
    private FirebaseUser currUser;
    private String uId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewordelete_password);

        Bundle bundle = getIntent().getExtras();

        final String passId = bundle.getString("pId");
        accTag = bundle.getString("account");
        passwrd = bundle.getString("password");

        accViewTextView = findViewById(R.id.accountView);
        passViewTextView = findViewById(R.id.passwordView);
        cancelPButton = findViewById(R.id.cancelPasswordButton);
        deletePButton = findViewById(R.id.deletePasswordButton);

        accViewTextView.setText(accTag);
        passViewTextView.setText(passwrd);

        mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("passwords");
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        uId = currUser.getUid();
//        String dbId = mDatabaseReference.getKey();

        deletePButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePassword(passId);
            }
        });

        cancelPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOperation();
            }
        });
    }

    private void deletePassword(String id) {
        mDatabaseReference2.child(uId).child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ViewOrDeletePassword.this, "Password deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
            }
        });
    }

    private void cancelOperation() {
        startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
    }
}