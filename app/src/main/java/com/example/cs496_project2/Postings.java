package com.example.cs496_project2;

public class Postings {
    String userID;
    String placeName;
    String date;
    String category;

    public String getUserID() {
        return userID;
    }
    public String getPlaceName() {
        return placeName;
    }
    public String getDate() {
        return date;
    }
    public String getCategory() {
        return category;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
