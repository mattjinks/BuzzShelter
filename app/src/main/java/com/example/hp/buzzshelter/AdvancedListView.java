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

    List<Shelter> tempGenderList = new ArrayList<>();

    List<Shelter> tempAgeList = new ArrayList<>();

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

        String genderVal = getIntent().getStringExtra("gender");
        String ageVal = getIntent().getStringExtra("age");

        System.out.println(genderVal);
        System.out.println(ageVal);



        InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);

        ShelterList shelterList = new ShelterList(inputStream);

        List<Shelter> shelters = shelterList.getShelterList();

        for (int i = 0; i < shelters.size(); i++) {
            if (shelters.get(i).getRestrictions().contains(genderVal)) {
                tempGenderList.add(shelters.get(i));
//                System.out.println(shelters.get(i).getName());
            }
            System.out.println(shelters.get(i).getRestrictions());
        }

        System.out.println(tempGenderList.size());
        for (int i = 0; i < shelters.size(); i++) {
            if (shelters.get(i).getRestrictions().contains(ageVal)) {
                tempAgeList.add(shelters.get(i));
                System.out.println(shelters.get(i).getName());
            }

        }

        System.out.println(tempAgeList.size());


        advancedSearchNames = new String[tempAgeList.size()];
        for (int i = 0; i < tempAgeList.size(); i++ ) {
            advancedSearchNames[i] = tempAgeList.get(i).getName();
        }

//        AdvancedSearchView sa = new AdvancedSearchView();
//
//        shelters = sa.getTempAgeList();
//
//        advancedNames = new String[shelters.size()];

        listView = (ListView) findViewById(R.id.advancedListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, advancedSearchNames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent userIntent = new Intent(AdvancedListView.this, ShelterView.class).putExtra("Shelter", tempAgeList.get(i));
                AdvancedListView.this.startActivity(userIntent);

            }
        });
    }

}
