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
import com.example.hp.buzzshelter.AdvancedSearchView.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdvancedListView extends AppCompatActivity {
    ArrayAdapter adapter;
    String[] advancedNames;
    ListView listView;
    List<Shelter> shelters = new ArrayList<>();

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



        AdvancedSearchView sa = new AdvancedSearchView();

        shelters = sa.getTempAgeList();

        advancedNames = new String[shelters.size()];

        for (int i = 0; i < shelters.size(); i++) {
            advancedNames[i] = shelters.get(i).getName();
        }
        listView = (ListView) findViewById(R.id.advancedListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, advancedNames);

        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent userIntent = new Intent(AdvancedListView.this, ShelterView.class).putExtra("Shelter", shelters.get(i));
//                AdvancedListView.this.startActivity(userIntent);
//
//            }
//        });
    }

}
