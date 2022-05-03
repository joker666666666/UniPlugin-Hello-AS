package io.dcloud.uniplugin.util;

import java.io.Serializable;

public class Address implements Serializable {
    private String name;
    private double longitude;
    private double latitude;

    public String getName(){
        return name;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public  void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public Address(String name, double longitude, double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
