package com.example.asm2.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Contact {

    private String firstName, lastName;
    private String phone, email;
    private LocalDate dob;
    private Group group;

    public Contact() {
    }

    public Contact(String fName, String lName, String phone, String email, LocalDate dob, Group group) {
        this.dob = dob;
        this.email = email;
        this.firstName = fName;
        this.lastName = lName;
        this.phone = phone;
        this.group = group;
    }

    public String getEmail() {
        return email;
    }



    public String getFirstName() {
        return firstName;
    }

    public Group getGroup() {
        return group;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String fName) {
        this.firstName= fName;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {

        return String.format("%-15s:%-15s:%-10s:%-25s:%-10s:%-10s\n",firstName,lastName,phone,email,dob,group);
    }

}

