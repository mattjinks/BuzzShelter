package com.example.hp.buzzshelter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class ShelterView extends AppCompatActivity {

    TextView nameField;
    TextView capacityField;
    TextView restrictionsField;
    TextView longitudeField;
    TextView latitudeField;
    TextView addressField;
    TextView phoneNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        nameField = findViewById(R.id.textView1);
        capacityField = findViewById(R.id.textView2);
        restrictionsField = findViewById(R.id.textView3);
        longitudeField = findViewById(R.id.textView4);
        latitudeField = findViewById(R.id.textView5);
        addressField = findViewById(R.id.textView6);
        phoneNumberField = findViewById(R.id.textView7);


        if (getIntent().getExtras() != null) {
            Shelter currentShelter = (Shelter) getIntent().getSerializableExtra("Shelter");
            if (currentShelter != null) {
                nameField.setText("Name: " + currentShelter.getName());
                capacityField.setText("Capacity: " + currentShelter.getCapacity());
                restrictionsField.setText("Restrictions: " + currentShelter.getRestrictions());
                longitudeField.setText("Longitude: " + currentShelter.getLongitude());
                latitudeField.setText("Latitude: " + currentShelter.getLatitude());
                addressField.setText("Address: " + currentShelter.getAddress());
                phoneNumberField.setText("Phone Number: " + currentShelter.getPhoneNumber());

            }
        }





    }

}
