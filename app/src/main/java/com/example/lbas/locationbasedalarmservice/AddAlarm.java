package com.example.lbas.locationbasedalarmservice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.location.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddAlarm extends ActionBarActivity implements LocationListener {
    EditText editTextSearch;
    String strSearch;

    EditText editTextTask;
    String strTask;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    DatabaseHandler db;
    /** Local variables **/
    GoogleMap googleMap;
    Boolean searchTF = false;
    String strLatitude;
    String strLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        //createMapView();
        //addMarker(72.8776559, 19.0759837);
        /*db = new DatabaseHandler(getApplicationContext());
        List<GetterSetter> getterSetterList = db.getAlarmList();
        for(GetterSetter gs : getterSetterList){
            //Log.d("Location",gs.getAALocation());
            Toast.makeText(getApplicationContext(),gs.getAALocation(),Toast.LENGTH_LONG).show();
        }
        db.close();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



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

            db.addAlarm(new GetterSetter(strSearch, strLatitude,strLongitude,"ON",strTask,"NO"));
            Toast.makeText(getApplicationContext(),"Alarm Saved Successfully",Toast.LENGTH_LONG).show();
            searchTF = false;
        }
        else{
            Toast.makeText(getApplicationContext(),"Please select a place to add to alarm",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    @Override
    public void onLocationChanged(Location location) {
        createMapView();
        addMarker(location.getLongitude(), location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
