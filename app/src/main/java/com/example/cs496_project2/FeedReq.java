package com.example.cs496_project2;

public class FeedReq {
    final String place;
    final String id;
    final Number like;
    final String contents;
    final String category;

    FeedReq(String place, String id, Number like, String contents, String category) {
        this.place = place;
        this.id = id;
        this.like = like;
        this.contents = contents;
        this.category =category;
    }
}