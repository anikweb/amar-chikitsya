package com.example.amarchikitsya.model;

public class NurseCare {
    int id;
    String name,nursingHome,address,mobileNumber,experience,gender;
    public NurseCare(int id, String name, String nursingHome, String address, String mobileNumber, String experience, String gender) {
        this.id = id;
        this.name = name;
        this.nursingHome = nursingHome;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.experience = experience;
        this.gender = gender;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNursingHome() {
        return nursingHome;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getExperience() {
        return experience;
    }

    public String getGender() {
        return gender;
    }
}
