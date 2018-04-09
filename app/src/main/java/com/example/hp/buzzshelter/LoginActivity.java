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


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String userID;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private ProgressDialog progressDialog;
    HomelessPerson hp;
    Admin admin;
    DatabaseReference user;
    String userType;
    private HomelessPerson userInstance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final List<HomelessPerson> homelessPeople = new ArrayList<HomelessPerson>();
        user = FirebaseDatabase.getInstance().getReference("Users").child("Users")
                .child("Homeless");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("LAsigned in", user.getUid());
                    Log.d(user.getEmail(),"");
                } else {
                    Log.d("signed", "out");
                }
            }
        };
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatabase.getReference();
        //progressDialog = new ProgressDialog(this);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bCancel = (Button) findViewById(R.id.bCancel);



        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //progressDialog.setMessage("Login in progress");
                //progressDialog.show();
                mAuth.signInWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, UserAppActivity.class);
                                    intent.putExtra("userID", userID);

                                   /* if (userType.equals("Homeless")) {
                                        intent.putExtra("homelessPerson", hp);
                                    } else if (userType.equals("Admin")) {
                                        intent.putExtra("admin", admin);
                                    }*/
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
