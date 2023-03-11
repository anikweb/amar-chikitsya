package com.example.amarchikitsya.model;

public class Ambulance {
    int id;
    String name,address,mobileNumber;

    public Ambulance(int id, String name, String address, String mobileNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
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

    public String getMobileNumber() {
        return mobileNumber;
    }
}
