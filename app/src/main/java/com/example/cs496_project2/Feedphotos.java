package com.example.cs496_project2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedphotos {
    @SerializedName("file_name")
    @Expose
    String file_name;
    @SerializedName("place")
    @Expose
    String place;
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("like")
    @Expose
    String like;
    @SerializedName("contents")
    @Expose
    String contents;
    @SerializedName("category")
    @Expose
    String category;


    public String getFile_name() {
        return file_name;
    }
    public String getPlace() {
        return place;
    }
    public String getId() {
        return id;
    }
    public String getLike() {
        return like;
    }
    public String getContents() {
        return contents;
    }
    public String getCategory() {
        return category;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setLike(String placeName) {
        this.place = placeName;
    }
    public void setContents(String date) {
        this.contents = date;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
