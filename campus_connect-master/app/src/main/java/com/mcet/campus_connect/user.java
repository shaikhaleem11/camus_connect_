package com.mcet.campus_connect;

public class user {
    public String name;
    public String email;
    public String uid;

    public user() {
        // default constructor required for Firebase
    }

    public user(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }
}
