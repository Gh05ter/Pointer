package com.example.zoomeye.ZoomEye;

/**
 * Created by MR.SJ on 2016/4/25.
 */
public class User {
    String username;
    String password;
   public User(String username,String password){
        this.username=username;
        this.password=password;
    }

     void setPassword(String password) {
        this.password = password;
    }

     void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
