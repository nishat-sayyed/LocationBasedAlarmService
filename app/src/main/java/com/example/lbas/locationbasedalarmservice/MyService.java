package com.example.lbas.locationbasedalarmservice;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

/**
 * Created by proit on 2/20/15.
 */
public class MyService extends Service {
    public static final String BROADCAST_ACTION = "com.example.lbas.locationbasedalarmservice.MainActivity";
        private final Handler handler = new Handler();
    Intent intent;
    int counter = 0;
    static boolean b=true;
    // GPSTracker class
    GPSTracker gps;

    DatabaseHandler db;

    @Override
    public void onCreate() {
    // Called on service created
        intent = new Intent(BROADCAST_ACTION);
        b=true;
    }

    @Override
    public void onDestroy() {
        // Called on service stopped
    }

    @Override
    public void onStart(Intent intent, int startid) {
        int i = 0;
        while (i < 101) {
            if (i > 100) {
                this.onDestroy();
            }
            else {
                counter = i;
                i++;
                handler.removeCallbacks(sendUpdatesToUI);
                handler.postDelayed(sendUpdatesToUI, 1 * 1000); // 1 sec
            }
        }

    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 1 * 1000); // 1 sec
        }
    };

    private void DisplayLoggingInfo() {
        // create class object
        gps = new GPSTracker(this);
        //intent.putExtra("time", new Date().toLocaleString());
        //intent.putExtra("counter", String.valueOf(counter));
        // check if GPS enabled
        if(gps.canGetLocation()){

            intent.putExtra("Lat", String.valueOf(gps.getLatitude()));
            intent.putExtra("Long", String.valueOf(gps.getLongitude()));


            String Lat = String.valueOf(gps.getLatitude()).substring(0,4);
            String Longitude = String.valueOf(gps.getLongitude()).substring(0,4);

                //Toast.makeText(getApplicationContext(),"Current:"+Lat+"."+Longitude,Toast.LENGTH_SHORT).show();

                Log.d("Current",Lat+","+Longitude);

                db = new DatabaseHandler(getApplicationContext());
                List<GetterSetter> getterSetterList = db.getAlarmListByStatus();
                for (GetterSetter gs : getterSetterList) {
                    //Toast.makeText(getApplicationContext(),"Current:"+Lat+"."+Longitude+"\nDB : Lat : "+gs.getAALatitude().substring(0,5) + " Long" + gs.getAALongitude().substring(0,5), Toast.LENGTH_SHORT).show();
                    Log.d("DB",gs.getAALatitude().substring(0,5)+","+gs.getAALongitude().substring(0,5));
                    if(!(gs.getAALatitude().equals("") && gs.getAALongitude().equals(""))) {
                        if (Lat.equals(gs.getAALatitude().substring(0, 4)) && Longitude.equals(gs.getAALongitude().substring(0, 4))) {
                            //Toast.makeText(getApplicationContext(), "NIKUL", Toast.LENGTH_LONG).show();
                            //db = new DatabaseHandler(getApplicationContext());
                            generateNotification(getApplicationContext(), Lat, Longitude);

                            /*NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            //use the flag FLAG_UPDATE_CURRENT to override any notification already there
                            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            Notification notification = new Notification(R.drawable.ic_launcher, "Some Text", System.currentTimeMillis());
                            notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;

                            notification.setLatestEventInfo(getApplicationContext(), "This is a notification Title", "Notification Text", contentIntent);
                            //10 is a random number I chose to act as the id for this notification
                            notificationManager.notify(10, notification);*/

                            db.updateAlarmListByLat(new GetterSetter(Lat));
                            //0db.close();

                            //stopService(new Intent(MainActivity.this, MyService.class));
                        }
                    }
                    //Toast.makeText(getApplicationContext(), Lat, Toast.LENGTH_LONG).show();
                }
                db.close();

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            //generateNotification(getApplicationContext(),String.valueOf(gps.getLatitude()),String.valueOf(gps.getLongitude()));
        }
        sendBroadcast(intent);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String latitude, String longitude) {
        try {
            int icon = R.drawable.ic_launcher;
            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(icon, "You have some work to do.", when);

            String title = context.getString(R.string.app_name);
            Intent notificationIntent = new Intent(context, AlarmTask.class);
            //notificationIntent.put
            notificationIntent.putExtra("lat",latitude);
            notificationIntent.putExtra("long",longitude);

            // set intent so it does not start a new activity
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent =
                    PendingIntent.getActivity(context, 0, notificationIntent, 0);
            notification.setLatestEventInfo(context, "Location Alarm", "Hi You need to get a work done.", intent);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            // Play default notification sound
            notification.defaults |= Notification.DEFAULT_SOUND;

            //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icc_world_cup_2015);

            // Vibrate if vibrate is enabled
            notification.defaults |= Notification.DEFAULT_VIBRATE;

            notificationManager.notify(0, notification);

            /*NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            Notification.Builder builder=new Notification.Builder(context);
            Intent intent=new Intent(context,AlarmTask.class);
            intent.setAction("notification");
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
            builder.setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Location")
            .setContentText(latitude+","+longitude)
            .setTicker("Ticker")
            .setLights(0xFFF0000,500,500)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

            Notification notification=builder.getNotification();
            notificationManager.notify(R.drawable.ic_launcher,notification);*/

            /*NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(context, MainActivity.class);

            //use the flag FLAG_UPDATE_CURRENT to override any notification already there
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new Notification(R.drawable.ic_launcher, "Some Text", System.currentTimeMillis());
            notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;

            notification.setLatestEventInfo(context, "This is a notification Title", "Notification Text", contentIntent);
            //10 is a random number I chose to act as the id for this notification
            notificationManager.notify(10, notification);*/


        }
        catch(Exception ex){
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isRunning() {
        if(b==true) {
            return true;
        }
        return false;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
