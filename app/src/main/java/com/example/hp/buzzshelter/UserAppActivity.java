package com.example.hp.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);
        final Button bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(UserAppActivity.this, WelcomeActivity.class);
                UserAppActivity.this.startActivity(welcomeIntent);
            }
        });

    }
}
