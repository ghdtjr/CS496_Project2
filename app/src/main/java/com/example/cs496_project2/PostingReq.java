package com.example.cs496_project2;

public class PostingReq {
    final String id;
    final String place;
    final String date;
    final String category;

    PostingReq(String id, String place, String date, String category) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.category = category;
    }
}