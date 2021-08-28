package com.example.asm3.entity;

import java.time.LocalDate;

public class Contact {

    private String firstName, lastName;
    private String phone, email;
    private LocalDate dob;
    private Group group;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String phone, String email, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fName) {
        this.firstName = fName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }



    @Override
    public String toString() {

        return String.format("%-15s:%-15s:%-10s:%-25s:%-10s:%-10s\n", firstName, lastName, phone, email, dob, group);
    }

}

