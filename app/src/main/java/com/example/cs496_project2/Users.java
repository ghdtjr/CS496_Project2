package com.example.cs496_project2;

public class Users {
    @SerializedName("id")
    @Expose
    String userID;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("phone_number")
    @Expose
    String phone_number;


    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

}
