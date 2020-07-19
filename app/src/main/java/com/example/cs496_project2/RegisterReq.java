package com.example.cs496_project2;

public class RegisterReq {
    final String id;
    final String password;
    final String phone_number;

    RegisterReq(String id, String password, String phone_number) {
        this.id = id;
        this.password = password;
        this.phone_number = phone_number;
    }
}
