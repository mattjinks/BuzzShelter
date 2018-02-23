package com.example.hp.buzzshelter;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    public static class UserDomain {
        private static Map<String, ArrayList> domain = new HashMap<>();

        protected void registerUser(ArrayList userInfo) {
            domain.put((String) userInfo.get(1), userInfo);
        }

        public boolean validateLogin(String username, String password) {
            if ((domain.get(username) != null) && password.equals(domain.get(username).get(2))) {
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etGender = (EditText) findViewById(R.id.etGender);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final Button bCancel = (Button) findViewById(R.id.bCancel);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = new ArrayList();
                list.add(etName.getText().toString());
                list.add(etUsername.getText().toString());
                list.add(etPassword.getText().toString());
                list.add(etAge.getText().toString());
                list.add(etGender.getText().toString());
                UserDomain domain = new UserDomain();
                domain.registerUser(list);
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                builder.setMessage("Registered!")
                        .create()
                        .show();
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                RegistrationActivity.this.startActivity(intent);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent userIntent = new Intent(RegistrationActivity.this, WelcomeActivity.class);
                RegistrationActivity.this.startActivity(userIntent);
            }
        });
    }
}
