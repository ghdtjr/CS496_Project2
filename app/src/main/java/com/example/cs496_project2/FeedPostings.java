package com.example.cs496_project2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedPostings {
    @SerializedName("file_name")
    @Expose
    String file_name;
    @SerializedName("place")
    @Expose
    String placeName;
    @SerializedName("id")
    @Expose
    String userID;
    @SerializedName("like")
    @Expose
    String like;
    @SerializedName("contents")
    @Expose
    String contents;
    @SerializedName("category")
    @Expose
    String category;

    public String getFileName() {
        return file_name;
    }
    public String getPlaceName() {
        return placeName;
    }
    public String getUserID() {
        return userID;
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
    
    public void setFileName(String file_name) {
        this.file_name = file_name;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setLike(String category) {
        this.like = like;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
