package com.example.hp.buzzshelter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAppActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private HomelessPerson userInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);
        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDatabase.getReference("Users").child("Users").child("Homeless");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        //Log.d(user.getEmail(),"");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("UAsigned in", user.getUid());
                    Log.d(user.getEmail(),"");

                } else {
                    Log.d("signed", "out");
                }
            }
        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("id",userID);
                    HomelessPerson homelessPerson = new HomelessPerson(ds.child(userID)
                            .getValue(HomelessPerson.class).getName(),ds.child(userID)
                            .getValue(HomelessPerson.class).getLoginEmail(),ds.child(userID)
                            .getValue(HomelessPerson.class).getPassword(),ds.child(userID)
                            .getValue(HomelessPerson.class).getGender(),ds.child(userID)
                            .getValue(HomelessPerson.class).getAge());
                    userInstance = homelessPerson;
                    //Log.d("We", userInstance.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });

        final Button bLogout = (Button) findViewById(R.id.bLogout);
        final Button viewShelters = (Button) findViewById(R.id.viewSheltersButton);
        final Button advancedSearch = (Button) findViewById(R.id.advancedSearchButton);

        bLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(UserAppActivity.this, WelcomeActivity.class);
                UserAppActivity.this.startActivity(welcomeIntent);
            }
        });

        viewShelters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(UserAppActivity.this, ShelterListView.class);
                welcomeIntent.putExtra("userInstance", userInstance);
                //Log.d("uaa",userInstance.getLoginEmail());

                UserAppActivity.this.startActivity(welcomeIntent);
            }
        });

        advancedSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent advancedSearchIntent = new Intent(UserAppActivity.this, AdvancedSearchView.class);
                UserAppActivity.this.startActivity(advancedSearchIntent);
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