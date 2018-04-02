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


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;





public class ShelterListView extends AppCompatActivity {
    ArrayAdapter adapter;
    String[] shelterNames;
    ListView listView;
    List<Shelter> shelters = new ArrayList<>();
    private HomelessPerson userInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list_view);


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

        InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);

        ShelterList shelterList = new ShelterList(inputStream);

        shelters = shelterList.getShelterList();


        shelterNames = new String[shelters.size()];

        for (int i = 0; i < shelters.size(); i++) {
            shelterNames[i] = shelters.get(i).getName();
        }

        listView = (ListView) findViewById(R.id.shelterListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shelterNames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent userIntent = new Intent(ShelterListView.this, ShelterView.class).putExtra("Shelter", shelters.get(i));
                userIntent.putExtra("hp", userInstance);
                //Log.d("slv",userInstance.getLoginEmail());
                ShelterListView.this.startActivity(userIntent);

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





}