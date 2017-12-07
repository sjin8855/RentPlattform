package com.example.sangjin_lee.rentplattform;

/**
 * Created by gmpark on 2017. 12. 1..
 */


public class User {
    private String userEmail;

    public User(String userEmail) {

        this.userEmail = userEmail;

    }

    public User() {

    }


    public  String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }
}
