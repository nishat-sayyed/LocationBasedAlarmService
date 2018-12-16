package com.example.lbas.locationbasedalarmservice;

public class GetterSetter {
    int _aa_id;
    String _aa_location;
    String _aa_latitude;
    String _aa_longitude;
    String _aa_task;
    String _aa_bookmark;
    String _aa_status;

    public GetterSetter(){

    }

    public GetterSetter(String aa_location, String aa_latitude, String aa_longitude, String aa_status, String aa_task, String aa_bookmark){
        this._aa_location = aa_location;
        this._aa_latitude = aa_latitude;
        this._aa_longitude = aa_longitude;
        this._aa_status = aa_status;
        this._aa_task = aa_task;
        this._aa_bookmark = aa_bookmark;
    }

    public GetterSetter(String aa_latitude){
        this._aa_latitude = aa_latitude;
    }

    //Get AA ID
    public int getAAID(){
        return this._aa_id;
    }

    //Set AA ID
    public void setAAID(int id){
        this._aa_id = id;
    }

    //Get AA Location
    public String getAALocation(){
        return this._aa_location;
    }

    //set AA Location
    public void setAALocation(String location){
        this._aa_location = location;
    }

    // Get AA Latitude
    public String getAALatitude(){
        return this._aa_latitude;
    }

    //set AA Latitude
    public void setAALatitude(String latitude){
        this._aa_latitude = latitude;
    }

    //Get AA Longitude
    public String getAALongitude(){
        return this._aa_longitude;
    }

    //set AA Longitude
    public void setAALongitude(String longitude){
        this._aa_longitude = longitude;
    }

    // Get AA Status
    public String getAAStatus(){
        return this._aa_status;
    }

    // Set AA Status
    public void setAAStatus(String status){
        this._aa_status = status;
    }

    // Get AA Task
    public String getAATask(){
        return this._aa_task;
    }

    // Set AA Task
    public void setAATask(String task){
        this._aa_task = task;
    }

    // Get AA Bookmark
    public String getAABookmark(){
        return this._aa_bookmark;
    }

    // Set AA Bookmark
    public void setAABookmark(String bookmark){
        this._aa_bookmark = bookmark;
    }
}
