package com.example.lbas.locationbasedalarmservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {
    DatabaseHandler db;
    int match = 0;

    String [] mnuList={"Add Alarm","View Alarm","Add Bookmarks","View Bookmarks","Nearby Location","Start Service","Stop Service","About Us"};
    int [] mnuIcon={R.drawable.addalarm,R.drawable.viewalarm,R.drawable.addbookmark,R.drawable.viewbookmarks,R.drawable.nearby,R.drawable.start,R.drawable.stop,R.drawable.about};


    CustomMenu customMenu;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        db.updateBookmark();
        db.close();
        startService(new Intent(MainActivity.this, MyService.class));
        MyService.b=true;
        lv=(ListView)findViewById(R.id.listView);
        customMenu=new CustomMenu(MainActivity.this,mnuList,mnuIcon);
        lv.setAdapter(customMenu);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mnuList[position].equals("Add Alarm")) {
                    Intent i = new Intent(MainActivity.this, AddAlarm.class);
                    startActivity(i);
                } else if (mnuList[position].equals("View Alarm")) {
                    Intent i = new Intent(MainActivity.this, ViewAlarmActivity.class);
                    startActivity(i);
                } else if (mnuList[position].equals("Add Bookmarks")) {
                    Intent i = new Intent(MainActivity.this,AddBookmark.class);
                    startActivity(i);
                } else if (mnuList[position].equals("View Bookmarks")) {
                    Intent i = new Intent(MainActivity.this,ViewBookmark.class);
                    startActivity(i);
                } else if (mnuList[position].equals("Nearby Location")) {
                    Intent i = new Intent(MainActivity.this,NearByMenu.class);
                    startActivity(i);
                } else if (mnuList[position].equals("Start Service")) {
                    if(MyService.isRunning()){
                        Toast.makeText(getApplication(),"Service Already Started",Toast.LENGTH_LONG).show();
                    }
                    else {
                        startService(new Intent(MainActivity.this, MyService.class));
                        MyService.b=true;
                        Toast.makeText(getApplication(), "Service Started", Toast.LENGTH_LONG).show();
                    }
                } else if (mnuList[position].equals("Stop Service")) {
                    if(MyService.isRunning()){
                        MyService.b=false;
                        stopService(new Intent(MainActivity.this, MyService.class));
                        Toast.makeText(getApplication(), "Service Stopped", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplication(),"Service Already Stopped",Toast.LENGTH_LONG).show();
                    }
                }
                else if (mnuList[position].equals("About Us")) {
                    Intent i = new Intent(MainActivity.this,AboutActivity.class);
                    startActivity(i);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(getApplication(),"LOCATION BASED SERVICE:\nHi, You Need to To Some Work Done",Toast.LENGTH_SHORT).show();
            updateDate(intent);
            //startActivity(intent);
        }
    };

    public void updateDate(Intent i){
        Handler h=new Handler(getApplicationContext().getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                /*try{

                    Uri notification=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r=RingtoneManager.getRingtone(getApplicationContext(),notification);
                    r.play();

                }
                catch (Exception ex){
                    Toast.makeText(getApplication(),ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }*/
            }
        }) ;


    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(
                MyService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}
