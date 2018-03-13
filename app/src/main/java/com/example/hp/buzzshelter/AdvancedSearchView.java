package com.example.hp.buzzshelter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.hp.buzzshelter.ShelterListView;

public class AdvancedSearchView extends AppCompatActivity {


    InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);

    ShelterList shelterList = new ShelterList(inputStream);

    List<Shelter> shelters = shelterList.getShelterList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search_view2);
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

        List<String> genderArray =  new ArrayList<String>();
        genderArray.add("Choose gender...");
        genderArray.add("Male");
        genderArray.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, genderArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.genderspinner);
        sItems.setAdapter(adapter);

        List<String> ageArray =  new ArrayList<String>();
        ageArray.add("Choose age range...");
        ageArray.add("Children");
        ageArray.add("Young Adults");
        ageArray.add("Families with Newborns");
        ageArray.add("Anyone");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ageArray);

        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerItems = (Spinner) findViewById(R.id.agespinner);
        spinnerItems.setAdapter(stringArrayAdapter);
    }

}