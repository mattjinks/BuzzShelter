package com.example.hp.buzzshelter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Twitter;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String userID;
    private FirebaseAuth.AuthStateListener mAuthListener;
    HomelessPerson hp;
    Admin admin;
    DatabaseReference user;
    String userType;
    private HomelessPerson userInstance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Twitter.initialize(this);

        final List<HomelessPerson> homelessPeople = new ArrayList<HomelessPerson>();
        //user = FirebaseDatabase.getInstance().getReference("Users").child("Homeless");
        Log.d("is user null", (user) + "");
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            //Log.d("common","j0");
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Log.d("being","called inside");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //Log.d("fuck",user.toString());
                if (user != null) {
                    Log.d("LAsigned in", user.getUid());
                    Log.d(user.getEmail(),"");
                } else {
                    Log.d("signed", "out");
                }
            }
        };*/
        //mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = mAuth.getCurrentUser();
        Log.d("skipping","sateListnere");
        //userID = user.getUid();
        //myRef = mFirebaseDatabase.getReference();
        //progressDialog = new ProgressDialog(this);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bCancel = (Button) findViewById(R.id.bCancel);
        final Button bForgot = (Button) findViewById(R.id.bForgot);

       



        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //progressDialog.setMessage("Login in progress");
                //progressDialog.show();
                mAuth.signInWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Log.d("signed", "in");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        userID = user.getUid();
                                    }
                                    Intent intent = new Intent(LoginActivity.this, UserAppActivity.class);
                                    intent.putExtra("userID", userID);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("!")
                                            .create()
                                            .show();
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    //progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .create()
                                            .show();
                                }
                            }
                        });
            }
        });

        bForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }*/
    }








}
