package com.example.lbas.locationbasedalarmservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NearByLocation extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_location);

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        String qry=b.getString("Search");

        WebView webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
        String Lat="";
        String Longitude="";
        if(gpsTracker.canGetLocation()){
            Lat = String.valueOf(gpsTracker.getLatitude());
            Longitude = String.valueOf(gpsTracker.getLongitude());
        }

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if (qry.equalsIgnoreCase("Search Manually"))
            webView.loadUrl("https://www.google.com/maps/search"+"/@"+Lat+","+Longitude+",16z");

        else if (qry.equalsIgnoreCase("Get Directions"))
            webView.loadUrl("https://www.google.com/maps/dir/"+"/@"+Lat+","+Longitude+",16z");

        else
            webView.loadUrl("https://www.google.com/maps/search/"+qry+"/@"+Lat+","+Longitude+",16z");
    }

}
