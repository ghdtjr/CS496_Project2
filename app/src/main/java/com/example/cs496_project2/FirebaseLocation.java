package com.example.cs496_project2;

import java.util.HashMap;
import java.util.Map;

public class FirebaseLocation {
    public String name;
    public String lat;
    public String lng;

    public FirebaseLocation(){}

    public FirebaseLocation(String name, String lat, String lng){
        this.name=name;
        this.lat=lat;
        this.lng=lng;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result=new HashMap<>();
        result.put("name",name);
        result.put("lat",lat);
        result.put("lng",lng);
        return result;
    }

}
