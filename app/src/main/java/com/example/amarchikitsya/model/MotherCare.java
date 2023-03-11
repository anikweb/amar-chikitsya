package com.example.amarchikitsya.model;

public class MotherCare {
    int id;
    String image,title,txt;

    public MotherCare() {
    }

    public MotherCare(int id, String image, String title, String txt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.txt = txt;
    }
    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getTxt() {
        return txt;
    }
}
