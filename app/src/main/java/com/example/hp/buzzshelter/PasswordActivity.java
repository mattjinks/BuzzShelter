package com.example.hp.buzzshelter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordActivity extends AppCompatActivity {

    private EditText etPasswordEmail;
    private Button bResetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        etPasswordEmail = (EditText) findViewById(R.id.etPasswordEmail);
        bResetPassword = (Button) findViewById(R.id.bResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        bResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = etPasswordEmail.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordActivity.this, "Reset Email Sent!",
                                    Toast.LENGTH_SHORT);
                            finish();
                            startActivity(new Intent(PasswordActivity.this,
                                    WelcomeActivity.class));
                        } else {
                            Toast.makeText(PasswordActivity.this, "Error Sending Email!",
                                    Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
    }
}
