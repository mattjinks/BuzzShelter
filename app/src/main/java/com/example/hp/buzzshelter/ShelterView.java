package com.example.hp.buzzshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ShelterView extends AppCompatActivity {

    TextView nameField;
    TextView capacityField;
    TextView restrictionsField;
    TextView longitudeField;
    TextView latitudeField;
    TextView addressField;
    TextView phoneNumberField;
    TextView vacancies;
    Button reserveButton;
    Button cancelReservationButton;
    EditText numberOfRooms;

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
        phoneNumberField = findViewById(R.id.textView10);
        vacancies = findViewById(R.id.textView7);


        //if (getIntent().getExtras() != null) {
        final Shelter currentShelter = (Shelter) getIntent().getSerializableExtra("Shelter");
        if (currentShelter != null) {
            nameField.setText("Name: " + currentShelter.getName());
            capacityField.setText("Capacity: " + currentShelter.getCapacity());
            restrictionsField.setText("Restrictions: " + currentShelter.getRestrictions());
            longitudeField.setText("Longitude: " + currentShelter.getLongitude());
            latitudeField.setText("Latitude: " + currentShelter.getLatitude());
            addressField.setText("Address: " + currentShelter.getAddress());
            phoneNumberField.setText("Phone Number: " + currentShelter.getPhoneNumber());
            vacancies.setText("Vacancies: " + currentShelter.getVacancies());

        }
        //}

        numberOfRooms = (EditText) findViewById(R.id.number_of_rooms);
        reserveButton = (Button) findViewById(R.id.reserve_button);
        cancelReservationButton = (Button) findViewById(R.id.cancel_reservation_button);
        int prevNumOfVancancies = 0;


        reserveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int inputRooms = Integer.parseInt(numberOfRooms.getText().toString());
                int curVacancies = Integer.parseInt(currentShelter.getVacancies());
                System.out.println(inputRooms);
                System.out.println(curVacancies);
                currentShelter.setName("These Nuts");
                if (curVacancies >= inputRooms) {
                    currentShelter.setVacancies("" + (curVacancies - inputRooms));
                    vacancies.setText("Vacancies: " + currentShelter.getVacancies());
                    Toast toast = Toast.makeText(getApplicationContext(), inputRooms + " reservation(s) made!",
                            Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Sorry, there are " + curVacancies + " vacancies at this time.",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
                System.out.println(inputRooms);
                System.out.println(currentShelter.getVacancies());
                System.out.println(curVacancies);
            }
        });


        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int currentNumOfReservations = numberOfRooms.getText().toString().equals("") ? 0 : Integer.parseInt(numberOfRooms.getText().toString());
                int curVacancies = Integer.parseInt(currentShelter.getVacancies());

                if ((curVacancies + currentNumOfReservations) <= Integer.parseInt(currentShelter.getCapacity())) {
                    currentShelter.setVacancies("" + (curVacancies + currentNumOfReservations));
                    vacancies.setText("Vacancies: " + currentShelter.getVacancies());
                }
            }
        });




    }

}
