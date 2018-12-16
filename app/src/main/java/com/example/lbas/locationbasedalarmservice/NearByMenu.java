package com.example.lbas.locationbasedalarmservice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NearByMenu extends ActionBarActivity {

    ListView lv;
    String [] nearby={"Hospitals","Medical","ATM","Bank","Police station","Fire Brigade","Hotel", "Restaurant", "College", "Search Manually", "Get Directions"};
    int [] mnuIcon={R.drawable.hospital,R.drawable.medical,R.drawable.atm,R.drawable.bank,R.drawable.police,R.drawable.firebrigade, R.drawable.hotel,R.drawable.restaurant,R.drawable.college,R.drawable.search,R.drawable.dir};
    CustomMenu customList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_menu);

        lv=(ListView)findViewById(R.id.listView);
        customList=new CustomMenu(NearByMenu.this,nearby,mnuIcon);
        lv.setAdapter(customList);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next=new Intent(NearByMenu.this,NearByLocation.class);
                next.putExtra("Search",nearby[position]);
                startActivity(next);
            }
        });
    }
}
