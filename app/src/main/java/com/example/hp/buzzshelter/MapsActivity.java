package com.example.hp.buzzshelter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final List<Shelter> shelters = new ArrayList<>();
    private static int numOfShelters = 0;
    private GoogleMap mMap;

    private FirebaseDatabase mFireBaseDatabase2;
    private DatabaseReference myRef2;
    private Shelter displayShelter;
    private HomelessPerson userInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        userInstance = (HomelessPerson) getIntent()
                .getSerializableExtra("hp");
        mFireBaseDatabase2 = FirebaseDatabase.getInstance();
        myRef2 = mFireBaseDatabase2.getReference("Shelters");
        setShelters();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        LatLng home = new LatLng(33.77, -84.396);
        //setShelters();
        Log.d("shelters",shelters.toString());




        String restrictionInput = getIntent().getStringExtra("restriction");

        for (int i = 0; i < shelters.size(); i++) {
            if (shelters.get(i).getRestrictions().contains(restrictionInput)) {
                Log.d(shelters.get(i).getName(),restrictionInput);
                LatLng shelterLatLong = new LatLng(Double.parseDouble(shelters.get(i).getLatitude()), Double.parseDouble(shelters.get(i).getLongitude()));
                mMap.addMarker(new MarkerOptions().position(shelterLatLong).title(shelters.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE))).setSnippet(getSnippet(shelterLatLong));
                doMarker(shelters.get(i));

            }

        }




//        setShelters();
//        for (int i = 0; i < numOfShelters; i++) {
//            LatLng shelterLatLong = new LatLng(Double.parseDouble(shelters.get(i).getLatitude()), Double.parseDouble(shelters.get(i).getLongitude()));
//            mMap.addMarker(new MarkerOptions().position(shelterLatLong).title(shelters.get(i).getName())
//                    .icon(BitmapDescriptorFactory.defaultMarker
//                            (BitmapDescriptorFactory.HUE_BLUE)));
//        }

//        LatLng sistersHouse = new LatLng(33.78,-84.41);
//        LatLng atlantaDayCenter = new LatLng(33.784889, -84.408771);
//        LatLng shepherdsInn = new LatLng(33.765162, -84.39265);
//        LatLng fuquaHall = new LatLng(33.76515, -84.392273);
//        LatLng atlantaChildrenCenter = new LatLng(33.770949, -84.384433);
//        LatLng edenVillage = new LatLng(33.762316, -84.43023);
//        LatLng ourHouse = new LatLng(33.759138, -84.371706);
//        LatLng covenantHouseGA = new LatLng(33.78823, -84.437988);
//        LatLng nicholasHouse = new LatLng(33.731823, -84.367953);
//        LatLng hopeAtlanta = new LatLng(33.753594, -84.390429);
//        LatLng gatewayCenter = new LatLng(33.747618, -84.394529);
//        LatLng youngAdultGuidanceCenter = new LatLng(33.789157, -84.470567);
//        LatLng homesOfLight = new LatLng(33.747641, -84.328691);
        float zoom = 15;
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(home).title("Georgia Tech")
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_BLUE)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoom));
        setMapLongClick(mMap);
        setPoiClick(mMap);
        enableMyLocation();



        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    void doMarker(final Shelter shelter) {
        //final Shelter shelter1 = shelter;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent userIntent = new Intent(MapsActivity.this, ShelterView.class).putExtra("Shelter", shelter);
                userIntent.putExtra("hp",userInstance);
                MapsActivity.this.startActivity(userIntent);


            }
        });

    }

    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f \n Occupancy: \n Restrictions: ",
                        latLng.latitude, latLng.longitude);
                map.addMarker(new MarkerOptions().position(latLng).title("Shelter").snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }

    private String getSnippet(LatLng latLng) {
        return String.format(Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f \n Occupancy: \n Restrictions: ",
                latLng.latitude, latLng.longitude);
    }
    private void setPoiClick(final GoogleMap map) {
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = mMap.addMarker(new MarkerOptions().position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    private void setShelters() {

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                android.util.Log.d("myTag", "This is my message");
            }
        });


    }
//        InputStream inputStream = getResources().openRawResource(R.raw.homelessshelterdatabase);
//
//        final List<String[]> resultList = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        try {
//            String csvLine;
//            while ((csvLine = reader.readLine()) != null) {
//                String[] row = csvLine.split(",");
//                resultList.add(row);
//            }
//        }
//        catch (IOException ex) {
//            throw new RuntimeException("Error in reading CSV file: "+ex);
//        }
//        finally {
//            try {
//                inputStream.close();
//            }
//            catch (IOException e) {
//                throw new RuntimeException("Error while closing input stream: "+e);
//            }
//        }
//        for (int i = 1; i < resultList.size(); i++) {
//
//            String first = resultList.get(i)[6];
//            first = first.substring(1);
//            String second = resultList.get(i)[7];
//            String third = resultList.get(i)[8];
//            third = third.substring(0, resultList.get(i)[8].length() - 1);
//            String address = first + second + third;
//
//            shelters.add(new Shelter(resultList.get(i)[1], resultList.get(i)[2], resultList.get(i)[3], resultList.get(i)[4], resultList.get(i)[5], address, resultList.get(i)[7], resultList.get(i)[10]));
//        }
//        numOfShelters = shelters.size();
//    }
}
