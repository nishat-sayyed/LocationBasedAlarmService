package com.example.lbas.locationbasedalarmservice;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;


public class AddBookmark extends ActionBarActivity {
    EditText editTextSearch;
    String strSearch;

    EditText editTextTask;
    String strTask;

    DatabaseHandler db;
    /** Local variables **/
    GoogleMap googleMap;
    Boolean searchTF = false;
    String strLatitude;
    String strLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookmark);
        createMapView();
        addMarker(72.8776559, 19.0759837);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_bookmark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker(double longitude,double latitude ){
        googleMap.clear();

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,longitude))
                            .title("Marker")
                            .draggable(true)
            );
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(12).tilt(30).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public void search(View view){
        double longitude=0;
        double latitude=0;
        editTextSearch = (EditText)findViewById(R.id.editTextSearch);
        editTextTask = (EditText)findViewById(R.id.editTextTask);
        strSearch = editTextSearch.getText().toString();
        strTask = editTextTask.getText().toString();
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(strSearch, 50);
            for(Address add : adresses){
                //if (statement) {//Controls to ensure it is right address such as country etc.
                longitude = add.getLongitude();
                latitude = add.getLatitude();
                //}
            }
            strLongitude = Double.toString(longitude);
            strLatitude = Double.toString(latitude);
            createMapView();
            addMarker(longitude,latitude);
            Toast.makeText(getApplicationContext(), ""+strLongitude+","+strLatitude, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchTF = true;
    }

    public void save(View view){
        db = new DatabaseHandler(getApplicationContext());
        if(searchTF == true){
            db.addAlarm(new GetterSetter(strSearch, strLatitude,strLongitude,"ON",strTask,"Yes"));
            Toast.makeText(getApplicationContext(),"Alarm Saved Successfully",Toast.LENGTH_LONG).show();
            searchTF = false;
        }
        else{
            Toast.makeText(getApplicationContext(),"Please select a place to add to alarm",Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
