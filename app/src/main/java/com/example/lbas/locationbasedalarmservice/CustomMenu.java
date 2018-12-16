package com.example.lbas.locationbasedalarmservice;

import android.app.Activity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by proit on 3/2/16.
 */
public class CustomMenu extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] mnuList;
    private final int[] icon;

    public CustomMenu(Activity context,String[] mnuList,int [] icon){
        super(context, R.layout.menulist, mnuList);
        this.context = context;
        this.mnuList = mnuList;
        this.icon = icon;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menulist, parent, false);

        TextView txtTitle = (TextView)rowView.findViewById(R.id.txtMenu);
        ImageView imgView = (ImageView)rowView.findViewById(R.id.imgView);

        txtTitle.setText(mnuList[position]);
        imgView.setImageResource(icon[position]);

        return rowView;
    }
}
