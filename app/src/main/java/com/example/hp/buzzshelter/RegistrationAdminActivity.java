package com.example.hp.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RegistrationAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_admin);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final Button bCancel = (Button) findViewById(R.id.bCancel);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = new ArrayList();
                list.add("n/a");
                list.add(etUsername.getText().toString());
                list.add(etPassword.getText().toString());
                list.add("n/a");
                list.add("n/a");
                RegistrationActivity.UserDomain domain = new RegistrationActivity.UserDomain();
                domain.registerUser(list);
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationAdminActivity.this);
                builder.setMessage("Registered!")
                        .create()
                        .show();
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