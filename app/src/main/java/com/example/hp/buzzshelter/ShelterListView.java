package com.example.hp.buzzshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;





public class ShelterListView extends AppCompatActivity {
    ArrayAdapter adapter;
    String[] shelterNames;
    ListView listView;
    List<Shelter> shelters = new ArrayList<>();
    private HomelessPerson userInstance;

    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private FirebaseDatabase mFireBaseDatabase2;
    private DatabaseReference myRef2;
    private Shelter displayShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list_view);

        mFireBaseDatabase2 = FirebaseDatabase.getInstance();
        myRef2 = mFireBaseDatabase2.getReference("Shelters");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        userInstance = (HomelessPerson) intent
                .getSerializableExtra("userInstance");
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDatabase.getReference("Shelters");
        //InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);

        //ShelterList shelterList = new ShelterList(inputStream);
        Log.d("shelterlistview",shelters.size()+"");
        //shelters = shelterList.getShelterList();
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("D",dataSnapshot.toString());
                HashMap shelterMap = (HashMap) dataSnapshot.getValue();
                Log.d("Iterable: ",shelterMap.toString());
                //final ArrayList<Shelter> mockShelters = new ArrayList<>();
                for (Object shelter : shelterMap.values()) {
                    HashMap map = (HashMap)shelter;
                    Shelter newShelter =new Shelter((String)map.get("name"), (String)map.get("capacity"),
                            (String)map.get("restrictions"), (String)map.get("longitude"),
                            (String)map.get("latitude"), (String)map.get("address"),
                            (String)map.get("specialNotes"), (String)map.get("phoneNumber"),
                            (String)map.get("vacancies"));
                    shelters.add(newShelter);
                    Log.d("lets look",newShelter.getName()+" "+newShelter.getVacancies());
//                    Log.d("shelterArrayLength",shelters.size() + "");
//                    Log.d("MockshelterArrayLength",mockShelters.size() + "");
//                    Log.d("newShelterinArray",shelters.get(shelters.size() - 1).getName());
//                    Log.d("newMockShelterinArray",mockShelters.get(mockShelters.size() - 1).getName());
                }

                shelterNames = new String[shelters.size()];

                for (int i = 0; i < shelters.size(); i++) {
                    shelterNames[i] = shelters.get(i).getName();
                    //addToDatabase(shelters.get(i));
                }

                listView = (ListView) findViewById(R.id.shelterListView);

                adapter = new ArrayAdapter<String>(ShelterListView.this, android.R.layout.simple_list_item_1, shelterNames);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.d("userBeingPassed", userInstance.getName());
                        Intent userIntent = new Intent(ShelterListView.this, ShelterView.class).putExtra("Shelter", shelters.get(i));
                        userIntent.putExtra("hp", userInstance);
                        //Log.d("slv",userInstance.getLoginEmail());
                        ShelterListView.this.startActivity(userIntent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView =  (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String r) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    public void addToDatabase(Shelter aShelter) {
        final Shelter currentShelter = aShelter;
        myRef.child("Shelters").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child(currentShelter.getName()).exists())) {
                    myRef.child(currentShelter.getName()).setValue(currentShelter);
                    Log.d("Shelters being added","");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });
    }


    public void getCurrentShelterFromArray(Shelter shelter) {
        Log.d("Get ShelterArray", shelters.toString());
        for (Shelter s : shelters) {
            if (s.getName().equals(shelter.getName())) {
                displayShelter = s;
                Log.d("whats in datase",s.getVacancies());
            }
        }
    }





}