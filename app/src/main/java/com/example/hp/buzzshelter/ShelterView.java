package com.example.hp.buzzshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


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


    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private HomelessPerson userInstance;

    private FirebaseDatabase mFireBaseDatabase2;
    private DatabaseReference myRef2;
    private Shelter displayShelter;
    private ArrayList<Shelter> shelters= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userInstance = (HomelessPerson) getIntent()
                .getSerializableExtra("hp");
        //Log.d("", userInstance.getLoginEmail());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDatabase.getReference("Users").child("Users").child("Homeless");
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        Log.d("get that email",user.getEmail());

        mFireBaseDatabase2 = FirebaseDatabase.getInstance();
        myRef2 = mFireBaseDatabase2.getReference("Shelters");


        final Shelter currentShelter = (Shelter) getIntent().getSerializableExtra("Shelter");



        //Log.d(user.getEmail(),"");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("ShelterView", user.getUid());
                    Log.d(user.getEmail(),"");

                } else {
                    Log.d("signed", "out");
                }
            }
        };

        nameField = findViewById(R.id.textView1);
        capacityField = findViewById(R.id.textView2);
        restrictionsField = findViewById(R.id.textView3);
        longitudeField = findViewById(R.id.textView4);
        latitudeField = findViewById(R.id.textView5);
        addressField = findViewById(R.id.textView6);
        phoneNumberField = findViewById(R.id.textView10);
        vacancies = findViewById(R.id.textView7);

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
        //addToDatabase(currentShelter); //add shelter to database

        numberOfRooms = (EditText) findViewById(R.id.number_of_rooms);
        reserveButton = (Button) findViewById(R.id.reserve_button);
        cancelReservationButton = (Button) findViewById(R.id.cancel_reservation_button);


        reserveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                int inputRooms = Integer.parseInt(numberOfRooms.getText().toString());
                int curVacancies = Integer.parseInt(currentShelter.getVacancies());

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getValue(HomelessPerson.class).getLoginEmail().equals(user.getEmail())) {
                                Log.d("id", userID);
                                HomelessPerson homelessPerson = ds.getValue(HomelessPerson.class);
                                userInstance = homelessPerson;
                                Log.d("userInstancetwo", userInstance.getName());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        android.util.Log.d("myTag", "This is my message");
                    }
                });

                if (!(userInstance.hasReservation())) {
                    if (curVacancies >= inputRooms) {
                        Log.d("reserving", userInstance.getName());
                        currentShelter.setVacancies("" + (curVacancies - inputRooms));
                        //vacancies.setText("Vacancies: " + (currentShelter.getVacancies()));
                        addToDatabase(currentShelter); //update shelter in database
                        userInstance.setReserved(true);
                        userInstance.addReservation(inputRooms);
                        updateUser(userInstance);
                        //shelters.clear();
                        //getSheltersFromDatabase(currentShelter);
                        Log.d("shelters.size",shelters.size()+"");
                        //getCurrentShelterFromArray(currentShelter);
                        vacancies.setText("Vacancies: " + (curVacancies - inputRooms));

                        Toast toast = Toast.makeText(getApplicationContext(), inputRooms + " reservation(s) made!",
                                Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(ShelterView.this, ShelterListView.class);
                        Log.d("userPassedSV", userInstance.getName());
                        intent.putExtra("userInstance", userInstance);
                        ShelterView.this.startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Sorry, there are " + curVacancies + " vacancies at this time.",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                    System.out.println(inputRooms);
                    System.out.println(currentShelter.getVacancies());
                    System.out.println(curVacancies);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have a reservation",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int vacancies = Integer.parseInt(currentShelter.getVacancies());

                if (vacancies < Integer.parseInt(currentShelter.getCapacity())) {
                    currentShelter.setVacancies("" + (vacancies + userInstance.getReservations()));
                    addToDatabase(currentShelter);
                }

                userInstance.setReserved(false);
                updateUser(userInstance);

                Intent intent = new Intent(ShelterView.this, ShelterListView.class);
                intent.putExtra("userInstance", userInstance);
                Toast toast = Toast.makeText(getApplicationContext(), "Reservation Cancelled",
                        Toast.LENGTH_LONG);
                toast.show();
                ShelterView.this.startActivity(intent);
            }
        });




    }

    /**
     * This method checks to see if Shelter already exists in database
     * if it doesn't exist it creates it/updates existing value
     * @param aShelter
     */
    public void addToDatabase(Shelter aShelter) {
        final Shelter currentShelter = aShelter;
        DatabaseReference databaseReference = myRef2.child("Shelters");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child(currentShelter.getName()).exists())) {
                    myRef2.child(currentShelter.getName()).setValue(currentShelter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });
    }



    public void updateUser(HomelessPerson hp) {
        final HomelessPerson homeless = hp;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if((dataSnapshot.child(userID).exists())) {

                    myRef.child(userID).setValue(homeless);                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
