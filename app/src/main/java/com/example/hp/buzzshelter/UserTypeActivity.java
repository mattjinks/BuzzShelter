package com.example.hp.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class UserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        final Button bAdmin = (Button) findViewById(R.id.bAdmin);
        final Button bUser = (Button) findViewById(R.id.bUser);

        bAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this, RegistrationAdminActivity.class);
                UserTypeActivity.this.startActivity(intent);
            }
        });


        bUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this, RegistrationActivity.class);
                UserTypeActivity.this.startActivity(intent);
            }
        });

    }
}
