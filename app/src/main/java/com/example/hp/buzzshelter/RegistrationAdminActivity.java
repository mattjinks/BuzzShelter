package com.example.hp.buzzshelter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistrationAdminActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_admin);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final Button bCancel = (Button) findViewById(R.id.bCancel);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Registering in progress");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim())
                        .addOnCompleteListener(RegistrationAdminActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Admin newUser = new Admin(etName.toString().trim(),
                                            etEmail.toString().trim(),
                                            etPassword.toString().trim());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationAdminActivity.this);
                                    builder.setMessage("Registered!")
                                            .create()
                                            .show();
                                    Intent intent = new Intent(RegistrationAdminActivity.this, LoginActivity.class);
                                    RegistrationAdminActivity.this.startActivity(intent);
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getApplicationContext(), "You are already registered",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationAdminActivity.this);
                                        builder.setMessage("FAIL!")
                                                .create()
                                                .show();
                                    }
                                }
                                //progressDialog.dismiss();
                            }
                        });

                Intent intent = new Intent(RegistrationAdminActivity.this, LoginActivity.class);
                RegistrationAdminActivity.this.startActivity(intent);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent userIntent = new Intent(RegistrationAdminActivity.this, WelcomeActivity.class);
                RegistrationAdminActivity.this.startActivity(userIntent);
            }
        });
    }
}