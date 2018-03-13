package com.example.hp.buzzshelter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.hp.buzzshelter.ShelterListView;

public class AdvancedSearchView extends AppCompatActivity {


    String agetext;
    String gendertext;
    Spinner genderSpinner;
    Spinner ageSpinner;


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

        // gender spinner
        List<String> genderArray =  new ArrayList<String>();
        genderArray.add("Men");
        genderArray.add("Women");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, genderArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner = (Spinner) findViewById(R.id.genderspinner);
        genderSpinner.setAdapter(adapter);


        // age spinner
        List<String> ageArray =  new ArrayList<String>();
        ageArray.add("Children");
        ageArray.add("Young adults");
        ageArray.add("Families w/ newborns");
        ageArray.add("Anyone");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ageArray);

        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner = (Spinner) findViewById(R.id.agespinner);
        ageSpinner.setAdapter(stringArrayAdapter);

        Button searchGenderButton = (Button) findViewById(R.id.searchGenderButton);
        Button searchAgeRangeButton = (Button) findViewById(R.id.searchAgeRangeButton);

        searchGenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gendertext = genderSpinner.getSelectedItem().toString();
                Intent advancedSearchIntent = new Intent(AdvancedSearchView.this, AdvancedListView.class);
                advancedSearchIntent.putExtra("criteria", "Gender");
                advancedSearchIntent.putExtra("gender", gendertext);
                AdvancedSearchView.this.startActivity(advancedSearchIntent);
            }
        });

        searchAgeRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agetext = ageSpinner.getSelectedItem().toString();
                Intent advancedSearchIntent = new Intent(AdvancedSearchView.this, AdvancedListView.class);
                advancedSearchIntent.putExtra("criteria", "Age Range");
                advancedSearchIntent.putExtra("age range", agetext);
                AdvancedSearchView.this.startActivity(advancedSearchIntent);
            }
        });

    }


}