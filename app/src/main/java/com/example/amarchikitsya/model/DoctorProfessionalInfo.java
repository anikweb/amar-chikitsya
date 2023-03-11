package com.example.amarchikitsya.model;

public class DoctorProfessionalInfo {
    String uid,specialization,designation,qualification,chamber;

    public DoctorProfessionalInfo() {
    }

    public DoctorProfessionalInfo(String uid, String specialization, String designation, String qualification, String chamber) {
        this.uid = uid;
        this.specialization = specialization;
        this.designation = designation;
        this.qualification = qualification;
        this.chamber = chamber;
    }

    public String getUid() {
        return uid;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDesignation() {
        return designation;
    }

    public String getQualification() {
        return qualification;
    }

    public String getChamber() {
        return chamber;
    }
}
