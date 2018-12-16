package com.example.lbas.locationbasedalarmservice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class AlarmDetails extends ActionBarActivity {
    TextView tvTask;
    TextView tvLocation;

    DatabaseHandler db;

    String a;
    String nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_details);
        tvTask = (TextView)findViewById(R.id.tvTask);
        tvLocation = (TextView)findViewById(R.id.tvLocation);

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        a = b.getString("id");
        nextActivity = b.getString("activity");

        db = new DatabaseHandler(getApplicationContext());
        List<GetterSetter> getterSetterList = db.getAlarmListById(a);
        for(GetterSetter gs : getterSetterList){
            tvTask.setText(gs.getAATask());
            tvLocation.setText(gs.getAALocation());
        }
        db.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_details, menu);
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

    public void delete(View view){
        db = new DatabaseHandler(getApplicationContext());
        db.aaDelete(a);
        db.close();
        if(nextActivity.equals("alarm")) {
            Intent i = new Intent(AlarmDetails.this, ViewAlarmActivity.class);
            startActivity(i);
            finish();
        }
        else if(nextActivity.equals("bookmark")){
            Intent i = new Intent(AlarmDetails.this, ViewBookmark.class);
            startActivity(i);
            finish();
        }
    }
}
