package com.example.marketmanager.models;

public class Account {
    String gmail;
    String password;

    public Account(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
    }

    public Account() {
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
