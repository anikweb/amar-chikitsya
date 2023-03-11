package com.example.amarchikitsya.model;

import java.util.List;

public class Doctors {

    int id;
    String name,specialized,address,qualification,mobile,chamber,photo;

    public Doctors() {
    }

    public Doctors(int id, String name, String specialized, String address, String qualification, String mobile, String chamber, String photo) {
        this.id = id;
        this.name = name;
        this.specialized = specialized;
        this.address = address;
        this.qualification = qualification;
        this.mobile = mobile;
        this.chamber = chamber;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialized() {
        return specialized;
    }

    public String getAddress() {
        return address;
    }

    public String getQualification() {
        return qualification;
    }

    public String getMobile() {
        return mobile;
    }

    public String getChamber() {
        return chamber;
    }

    public String getPhoto() {
        return photo;
    }
}