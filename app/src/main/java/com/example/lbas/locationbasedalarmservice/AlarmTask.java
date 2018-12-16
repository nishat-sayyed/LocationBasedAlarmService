package com.example.lbas.locationbasedalarmservice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AlarmTask extends ActionBarActivity {
    String latitude;
    String longitude;

    DatabaseHandler db;
    TextView tvTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_task);

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        latitude = b.getString("lat");
        longitude = b.getString("long");
        tvTask = (TextView)findViewById(R.id.tvtask);
        //Toast.makeText(getApplicationContext(),"Latitude "+ latitude + " Longitude "+ longitude,Toast.LENGTH_LONG).show();
        db = new DatabaseHandler(getApplicationContext());
        List<GetterSetter> getterSetterList = db.getAlarmListByLatLong(latitude,longitude);
        for(GetterSetter gs : getterSetterList){
            tvTask.append(gs.getAATask() + "\n-----------------\n");
            //Toast.makeText(getApplicationContext(), Lat, Toast.LENGTH_LONG).show();
        }
        db.updateAlarmListByLat(new GetterSetter(latitude));
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_task, menu);
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
}
