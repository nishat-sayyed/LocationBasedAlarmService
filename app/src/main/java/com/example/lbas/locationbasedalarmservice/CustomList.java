package com.example.lbas.locationbasedalarmservice;

import android.app.Activity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList  extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;

    public CustomList(Activity context,String[] web){
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent){
        Display mDisplay = context.getWindowManager().getDefaultDisplay();
        final int width  = mDisplay.getWidth();
        final int height = mDisplay.getHeight();
        //Log.e("Width",""+width);
        //Log.e("height",""+height);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single,parent, false);

        rowView.setMinimumHeight(80);
        TextView txtTitle = (TextView)rowView.findViewById(R.id.txt);
        if(width < 490){
            if(web[position].length() >15){
                txtTitle.setText(web[position].substring(0, 15) +"...");
            }
            else{
                txtTitle.setText(web[position]);
            }
        }
        else{
            txtTitle.setText(web[position]);
        }
        return rowView;
    }
}
