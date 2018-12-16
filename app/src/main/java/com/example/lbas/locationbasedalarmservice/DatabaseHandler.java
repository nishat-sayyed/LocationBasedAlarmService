package com.example.lbas.locationbasedalarmservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lbasdb";

    //table name
    private static final String TABLE_ADD_ALARM = "add_alarm_table";

    //column names
    private static final String AA_ID = "id";
    private static final String AA_LOCATION = "location";
    private static final String AA_LATITUDE = "latitude";
    private static final String AA_LONGITUDE = "longitude";
    private static final String AA_TASK = "task";
    private static final String AA_BOOKMARK = "bookmark";
    private static final String AA_STATUS = "status";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ADD_ALARM = "CREATE TABLE IF NOT EXISTS "+ TABLE_ADD_ALARM +" (" + AA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+ AA_LOCATION +" TEXT, "+AA_LATITUDE+" TEXT, "+AA_LONGITUDE+" TEXT, "+AA_TASK +" TEXT, "+AA_BOOKMARK+" TEXT, "+AA_STATUS+" TEXT)";
        db.execSQL(CREATE_TABLE_ADD_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addAlarm(GetterSetter getterSetter){
        SQLiteDatabase sd = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AA_LOCATION,getterSetter.getAALocation());
        values.put(AA_LATITUDE,getterSetter.getAALatitude());
        values.put(AA_LONGITUDE,getterSetter.getAALongitude());
        values.put(AA_TASK,getterSetter.getAATask());
        values.put(AA_STATUS,getterSetter.getAAStatus());
        values.put(AA_BOOKMARK,getterSetter.getAABookmark());
        sd.insert(TABLE_ADD_ALARM, null, values);
        sd.close();
    }

    public List<GetterSetter> getAlarmList(){
        List<GetterSetter> aaList = new ArrayList<GetterSetter>();
        String query = "SELECT * FROM "+TABLE_ADD_ALARM + " order by "+ AA_ID +" desc";
        SQLiteDatabase sd = this.getWritableDatabase();
        Cursor cursor = sd.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                GetterSetter getterSetter = new GetterSetter();
                getterSetter.setAAID(Integer.parseInt(cursor.getString(0)));
                getterSetter.setAALocation(cursor.getString(1));
                getterSetter.setAALatitude(cursor.getString(2));
                getterSetter.setAALongitude(cursor.getString(3));
                getterSetter.setAATask(cursor.getString(4));
                getterSetter.setAABookmark(cursor.getString(5));
                getterSetter.setAAStatus(cursor.getString(6));
                aaList.add(getterSetter);
            }while(cursor.moveToNext());
        }
        return  aaList;
    }

    public List<GetterSetter> getAlarmListByStatus(){
        List<GetterSetter> aaList = new ArrayList<GetterSetter>();
        String query = "SELECT * FROM "+TABLE_ADD_ALARM + " where status='ON' order by "+ AA_ID +" desc";
        SQLiteDatabase sd = this.getWritableDatabase();
        Cursor cursor = sd.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                GetterSetter getterSetter = new GetterSetter();
                getterSetter.setAAID(Integer.parseInt(cursor.getString(0)));
                getterSetter.setAALocation(cursor.getString(1));
                getterSetter.setAALatitude(cursor.getString(2));
                getterSetter.setAALongitude(cursor.getString(3));
                getterSetter.setAATask(cursor.getString(4));
                getterSetter.setAABookmark(cursor.getString(5));
                getterSetter.setAAStatus(cursor.getString(6));
                aaList.add(getterSetter);
            }while(cursor.moveToNext());
        }
        return  aaList;
    }

    public List<GetterSetter> getAlarmListByBookmark(String bookmark){
        List<GetterSetter> aaList = new ArrayList<GetterSetter>();
        String query = "SELECT * FROM "+TABLE_ADD_ALARM + " where "+ AA_BOOKMARK +"='"+bookmark+"' and "+ AA_STATUS +"='ON' order by "+ AA_ID +" desc";
        SQLiteDatabase sd = this.getWritableDatabase();
        Cursor cursor = sd.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                GetterSetter getterSetter = new GetterSetter();
                getterSetter.setAAID(Integer.parseInt(cursor.getString(0)));
                getterSetter.setAALocation(cursor.getString(1));
                getterSetter.setAALatitude(cursor.getString(2));
                getterSetter.setAALongitude(cursor.getString(3));
                getterSetter.setAATask(cursor.getString(4));
                getterSetter.setAABookmark(cursor.getString(5));
                getterSetter.setAAStatus(cursor.getString(6));
                aaList.add(getterSetter);
            }while(cursor.moveToNext());
        }
        return  aaList;
    }

    public List<GetterSetter> getAlarmListById(String id){
        List<GetterSetter> aaList = new ArrayList<GetterSetter>();
        String query = "SELECT * FROM "+TABLE_ADD_ALARM + " where "+ AA_ID +"='"+id+"'";
        SQLiteDatabase sd = this.getWritableDatabase();
        Cursor cursor = sd.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                GetterSetter getterSetter = new GetterSetter();
                getterSetter.setAAID(Integer.parseInt(cursor.getString(0)));
                getterSetter.setAALocation(cursor.getString(1));
                getterSetter.setAALatitude(cursor.getString(2));
                getterSetter.setAALongitude(cursor.getString(3));
                getterSetter.setAATask(cursor.getString(4));
                getterSetter.setAABookmark(cursor.getString(5));
                getterSetter.setAAStatus(cursor.getString(6));
                aaList.add(getterSetter);
            }while(cursor.moveToNext());
        }
        return  aaList;
    }

    public List<GetterSetter> getAlarmListByLatLong(String latitude, String longitude){
        List<GetterSetter> aaList = new ArrayList<GetterSetter>();
        String query = "SELECT * FROM "+TABLE_ADD_ALARM + " where "+ AA_LATITUDE +" like '"+latitude+"%' and "+ AA_LONGITUDE +" like '"+ longitude +"%'";
        Log.e("Query",query);
        SQLiteDatabase sd = this.getWritableDatabase();
        Cursor cursor = sd.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                GetterSetter getterSetter = new GetterSetter();
                getterSetter.setAAID(Integer.parseInt(cursor.getString(0)));
                getterSetter.setAALocation(cursor.getString(1));
                getterSetter.setAALatitude(cursor.getString(2));
                getterSetter.setAALongitude(cursor.getString(3));
                getterSetter.setAATask(cursor.getString(4));
                getterSetter.setAABookmark(cursor.getString(5));
                getterSetter.setAAStatus(cursor.getString(6));
                aaList.add(getterSetter);
            }while(cursor.moveToNext());
        }
        return  aaList;
    }

    public void updateAlarmListByLat(GetterSetter gs){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AA_STATUS, "OFF");
        db.update(TABLE_ADD_ALARM, values, AA_LATITUDE + " like ?", new String[]{String.valueOf(gs.getAALatitude()+"%")});
        db.close(); // Closing database connection
    }

    public void updateBookmark(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AA_STATUS, "ON");
        db.update(TABLE_ADD_ALARM, values, AA_BOOKMARK + " = ?", new String[]{String.valueOf("Yes")});
        db.close(); // Closing database connection
    }

    public void aaDelete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADD_ALARM, AA_ID + " = ?", new String[] { id });
        db.close();
    }
}
