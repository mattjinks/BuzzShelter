package com.example.hp.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsermame);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bCancel = (Button) findViewById(R.id.bCancel);

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                validate(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent userIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                LoginActivity.this.startActivity(userIntent);
            }
        });
    }

    private void validate(String username, String password) {
        RegistrationActivity.UserDomain userDomain = new RegistrationActivity.UserDomain();
        if (userDomain.validateLogin(username, password)) {
            Intent intent = new Intent(LoginActivity.this, UserAppActivity.class);
            LoginActivity.this.startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Login Failed")
                    .create()
                    .show();
        }
    }
}
