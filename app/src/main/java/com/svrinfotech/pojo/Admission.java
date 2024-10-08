package com.svrinfotech.pojo;

import java.io.Serializable;

/**
 * Created by Durgesh on 2/28/2018.
 */

public class Admission implements Serializable {

    private String name,dob,contactno,gender,email,qualification,course,fee,duration,doj;

    public Admission() {


    }

    public Admission(String name, String dob, String contactno, String gender, String email, String qualification, String course, String fee, String duration, String doj) {
        this.name = name;
        this.dob = dob;
        this.contactno = contactno;
        this.gender = gender;
        this.email = email;
        this.qualification = qualification;
        this.course = course;
        this.fee = fee;
        this.duration = duration;
        this.doj = doj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }
}
