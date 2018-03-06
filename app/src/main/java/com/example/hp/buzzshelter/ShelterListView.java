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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.IOException;




public class ShelterListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list_view);
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




        InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);

        final List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }


        final List<Shelter> shelters = new ArrayList<>();



        for (int i = 1; i < resultList.size(); i++) {

            String first = resultList.get(i)[6];
            first = first.substring(1);
            String second = resultList.get(i)[7];
            String third = resultList.get(i)[8];
            third = third.substring(0, resultList.get(i)[8].length() - 1);
            String address = first + second + third;

            shelters.add(new Shelter(resultList.get(i)[1], resultList.get(i)[2], resultList.get(i)[3], resultList.get(i)[4], resultList.get(i)[5], address, resultList.get(i)[7], resultList.get(i)[10]));


        }


        String[] shelterNames = new String[shelters.size()];

        for (int i = 0; i < shelters.size(); i++) {
            shelterNames[i] = shelters.get(i).getName();
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shelterNames);
        ListView listView = (ListView) findViewById(R.id.shelterListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent userIntent = new Intent(ShelterListView.this, ShelterView.class).putExtra("Shelter", shelters.get(i));
                ShelterListView.this.startActivity(userIntent);

            }
        });

    }

}
