package com.example.amarchikitsya.model;

public class User {

    String userId, name, email, phone, profileImage,address,birthdate,bloodGroup, gender,profession,role,userUpdatedAt;
    long userCreatedAt;

    public User() {
    }


    public User(String userId, String name, String email, String phone, String profileImage, String address, String birthdate, String bloodGroup, String gender, String profession, String pass, String role, String userUpdatedAt, long userCreatedAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
        this.address = address;
        this.birthdate = birthdate;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.profession = profession;

        this.role = role;
        this.userUpdatedAt = userUpdatedAt;
        this.userCreatedAt = userCreatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getGender() {
        return gender;
    }

    public String getProfession() {
        return profession;
    }

    public String getRole() {
        return role;
    }

    public String getUserUpdatedAt() {
        return userUpdatedAt;
    }

    public long getUserCreatedAt() {
        return userCreatedAt;
    }
}

