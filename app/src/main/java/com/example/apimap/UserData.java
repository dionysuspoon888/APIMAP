package com.example.apimap;

import java.util.TreeMap;

/**
 * Created by D on 10/18/2018.
 */

public class UserData {
    private double latitude;
    private double longitude;
    private String picture;
    private String _id;
    private String name;
    private String email;



    public UserData(double latitude,double longitude,String picture,String _id,String name,String email){
        this.latitude = latitude;
        this.longitude = longitude;
        this.picture = picture;
        this._id = _id;
        this.name = name;
        this.email = email;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
