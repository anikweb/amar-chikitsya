package com.example.amarchikitsya.model;

public class Diagnostics {

    int id;
    String name,address,mobile,opening_hour,closing_hour, day_off;

    public Diagnostics(int id, String name, String address, String mobile, String opening_hour, String closing_hour, String day_off) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.opening_hour = opening_hour;
        this.closing_hour = closing_hour;
        this.day_off = day_off;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOpening_hour() {
        return opening_hour;
    }

    public String getClosing_hour() {
        return closing_hour;
    }

    public String getDay_off() {
        return day_off;
    }
}