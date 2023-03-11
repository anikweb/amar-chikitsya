package com.example.amarchikitsya.model;

public class Pharmacy {
    int id;
    String name,address,city,number;
    
    public Pharmacy(int id, String name, String address, String city, String number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.number = number;
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

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }
}
