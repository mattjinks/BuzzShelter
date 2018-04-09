package com.example.hp.buzzshelter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;


public class MapSearchActivity extends AppCompatActivity {
    String restrictiontext;
    Spinner genderSpinner;
    Spinner restrictionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);



        // restriction spinner
        List<String> restrictionArray = new ArrayList<String>();
            restrictionArray.add("Children");
            restrictionArray.add("Young adults");
            restrictionArray.add("Families w/ newborns");
            restrictionArray.add("Anyone");
            restrictionArray.add("Men");
            restrictionArray.add("Women");
            restrictionArray.add("Veterans");


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, restrictionArray);

            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restrictionSpinner = (Spinner) findViewById(R.id.restrictionSpinner);
            restrictionSpinner.setAdapter(stringArrayAdapter);

        Button map = (Button) findViewById(R.id.bGoToMap);

            map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restrictiontext = restrictionSpinner.getSelectedItem().toString();
                Intent advancedSearchIntent = new Intent(MapSearchActivity.this, MapsActivity.class);
                advancedSearchIntent.putExtra("restriction", restrictiontext);
                MapSearchActivity.this.startActivity(advancedSearchIntent);
            }
        });
    }
}
