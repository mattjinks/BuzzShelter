package com.example.hp.buzzshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class AdvancedListView extends AppCompatActivity {
    ArrayAdapter adapter;
    ListView listView;

    List<Shelter> searchList = new ArrayList<>();


    List<Shelter> finalList = new ArrayList<>();

    String[] advancedSearchNames;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_list_view);
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

        String criteria = getIntent().getStringExtra("criteria");

        InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);

        ShelterList shelterList = new ShelterList(inputStream);

        List<Shelter> shelters = shelterList.getShelterList();

        //for (int i = 0; i < shelters.size(); i++) {
        //    System.out.println(shelters.get(i).getRestrictions());
        //}

        if (criteria.equals("Gender")) {

            String genderInput = getIntent().getStringExtra("gender");

            for (int i = 0; i < shelters.size(); i++) {
                if (shelters.get(i).getRestrictions().contains(genderInput)) {
                    searchList.add(shelters.get(i));
                }
            }

        } else {

            String ageRangeInput = getIntent().getStringExtra("age range");

            for (int i = 0; i < shelters.size(); i++) {
                if (shelters.get(i).getRestrictions().contains(ageRangeInput)) {
                    searchList.add(shelters.get(i));
                }
            }

        }

        advancedSearchNames = new String[searchList.size() + 1];

        advancedSearchNames[0] = "hi";
        for (int i = 1; i < advancedSearchNames.length; i++ ) {
            advancedSearchNames[i] = searchList.get(i - 1).getName();
        }

        listView = (ListView) findViewById(R.id.advancedListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, advancedSearchNames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent userIntent = new Intent(AdvancedListView.this, ShelterView.class).putExtra("Shelter", searchList.get(i + 1));
                AdvancedListView.this.startActivity(userIntent);

            }
        });
    }

}
