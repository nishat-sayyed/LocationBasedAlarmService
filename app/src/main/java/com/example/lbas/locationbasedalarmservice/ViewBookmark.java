package com.example.lbas.locationbasedalarmservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class ViewBookmark extends ActionBarActivity {
    ListView list;
    DatabaseHandler db;
    String[] emptyList = new String[1];
    String[] web;
    String[] aa_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookmar);

        db = new DatabaseHandler(getApplicationContext());
        List<GetterSetter> getterSetterList = db.getAlarmListByBookmark("Yes");
        web = new String[getterSetterList.size()];
        aa_id = new String[getterSetterList.size()];
        int i = 0;
        for(GetterSetter gs : getterSetterList){
            //Log.d("Location",gs.getAALocation());
            web[i] = gs.getAATask();
            aa_id[i] = Integer.toString(gs.getAAID());
            i++;
        }
        db.close();
        try{
            list=(ListView)findViewById(R.id.list);
            addToList();
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(ViewBookmark.this, AlarmDetails.class);
                    intent.putExtra("activity","bookmark");
                    intent.putExtra("id",aa_id[+ position]);
                    startActivity(intent);
                    finish();
                }
            });
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ViewBookmark.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            db = new DatabaseHandler(getApplicationContext());
                            db.aaDelete(aa_id[+ position]);
                            addToList();
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();
                    return true;
                }
            });
        }
        catch(Exception e){
            Log.e("Error", e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_bookmar, menu);
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

    public void addToList(){
        emptyList[0] = "";
        CustomList emptyAdapter = new CustomList(ViewBookmark.this, emptyList);
        list.setAdapter(emptyAdapter);
        CustomList adapter = new CustomList(ViewBookmark.this, web);
        list.setAdapter(adapter);
        if(web.length<=0)
        {
            Toast.makeText(ViewBookmark.this,"No Bookmark Found",Toast.LENGTH_SHORT).show();
        }
    }
}
