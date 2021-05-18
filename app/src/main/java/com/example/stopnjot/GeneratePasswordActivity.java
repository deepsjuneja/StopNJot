package com.example.stopnjot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Random;

public class GeneratePasswordActivity extends AppCompatActivity {
    EditText accountText, passwordLen;
    Button generatePasswordButton, doneButton, cancelGenPassButton;
    TextView genPassView;
    String accountTag, genPassword;
    int passLen;
    private DatabaseReference databaseReference2;
    private FirebaseUser currUser;
    private String uId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatepassword);

        accountText = findViewById(R.id.passwordTagText);
        passwordLen = findViewById(R.id.passwordLength);
        genPassView = findViewById(R.id.genPasswordView);
        doneButton = findViewById(R.id.afterGenDoneButton);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("passwords");
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        uId = currUser.getUid();

        generatePasswordButton = findViewById(R.id.generatePasswordButton);
        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePassword(v);
            }
        });

        cancelGenPassButton = findViewById(R.id.cancelGenPasswordButton);
        cancelGenPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOperation();
            }
        });
    }

    public void generatePassword(View view) {
        accountTag = accountText.getText().toString();
        passLen = Integer.parseInt(passwordLen.getText().toString());

        if(TextUtils.isEmpty(accountTag))
            return;

        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
                +"jklmnopqrstuvwxyz!@#$%&";

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(passLen);
        for (int i = 0; i < passLen; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));

        genPassword = sb.toString();
        genPassView.setText(genPassword);

        generatePassword(accountTag, genPassword);
    }

    private void generatePassword(String account, String password) {
        String id = databaseReference2.child(uId).push().getKey();
        Password passList = new Password(id, account, password);

        databaseReference2.child(uId).child(id).setValue(passList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(GeneratePasswordActivity.this, "Password generated", Toast.LENGTH_SHORT).show();
                generatePasswordButton.setText("GENERATED");
                genPassView.setVisibility(View.VISIBLE);
                doneButton.setVisibility(View.VISIBLE);

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
                    }
                });
            }
        });
    }

    private void cancelOperation() {
        startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
    }
}