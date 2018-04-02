package com.example.hp.buzzshelter;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    Spinner _genderSpinner;
    Spinner _ageSpinner;
    private ProgressDialog progressDialog;


    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private HomelessPerson userInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDatabase.getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        progressDialog = new ProgressDialog(this);


        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        List<String> genderArray =  new ArrayList<String>();
        genderArray.add("Men");
        genderArray.add("Women");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, genderArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _genderSpinner = (Spinner) findViewById(R.id._genderSpinner);
        _genderSpinner.setAdapter(adapter);
        final String gender = _genderSpinner.getSelectedItem().toString();

        List<String> ageArray =  new ArrayList<String>();
        ageArray.add("Children");
        ageArray.add("Young adults");
        ageArray.add("Families w/ newborns");
        ageArray.add("Anyone");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ageArray);

        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _ageSpinner = (Spinner) findViewById(R.id._ageSpinner);
        _ageSpinner.setAdapter(stringArrayAdapter);
        final String age = _ageSpinner.getSelectedItem().toString();

        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final Button bCancel = (Button) findViewById(R.id.bCancel);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("added", user.getUid());
                } else {
                    Log.d("failed", "to add");
                }
            }
        };

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Registering in progress");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim())
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Log.d("after:", mAuth.getCurrentUser().getUid());
                                    userID = mAuth.getCurrentUser().getUid();

                                    final HomelessPerson newUser = createNewUser(etName.getText().toString().trim(),
                                            etEmail.getText().toString().trim(),
                                            etPassword.getText().toString().trim(), gender, age);
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(!(dataSnapshot.child("Homeless")
                                                    .child(userID).exists())) {

                                                myRef.child("Homeless")
                                                        .child(userID).setValue(newUser);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            android.util.Log.d("myTag", "This is my message");
                                        }
                                    });
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                    builder.setMessage("Success!")
                                            .create()
                                            .show();
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    intent.putExtra("userID", userID);
                                    RegistrationActivity.this.startActivity(intent);
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "You are already registered",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                        builder.setMessage("FAIL!")
                                                .create()
                                                .show();
                                    }
                                }
                                //progressDialog.dismiss();
                            }
                        });
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent userIntent = new Intent(RegistrationActivity.this, WelcomeActivity.class);
                RegistrationActivity.this.startActivity(userIntent);
            }
        });
    }

    public HomelessPerson createNewUser(String name, String email, String password, String gender, String age) {
        HomelessPerson newUser = new HomelessPerson(name,
                email, password, gender, age);
        return newUser;
    }

    public void addToDatabase(HomelessPerson homelessUser) {
        final HomelessPerson person = homelessUser;
        DatabaseReference databaseReference = myRef.child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Homeless")
                        .child(userID).exists())) {

                    myRef.child("Homeless")
                            .child(userID).setValue(person);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });
    }

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