package com.bungae1112.test;

public class UserData {
    private String id;
    private  String pass;

    public UserData (String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public String  getId() {
        return  this.id;
    }

    public String getPass() {
        return this.pass;
    }
}
