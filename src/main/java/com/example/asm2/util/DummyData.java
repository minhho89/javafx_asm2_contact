package com.example.asm2.util;

import com.example.asm2.entity.Contact;
import com.example.asm2.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class DummyData {
    static Group family = new Group("Family");
    static Group friend = new Group("Friend");
    static Group colleagues = new Group("Colleagues");

    public static ObservableList<Contact> addContactData() {
        Contact john = new Contact("John", "Snow", "84987234123", "john@gmail.com", LocalDate.of(1987, 12, 31), family);
        Contact adam = new Contact("Adam", "Smith", "84980720100", "adam.smith@gmail.com", LocalDate.of(1990, 07, 22), friend);
        Contact jean = new Contact("Jean", "Tonogbanua", "85231678987", "jean@yahoo.com", LocalDate.of(1993, 9, 9), family);
        Contact an = new Contact("An", "Ha", "84123098345", "AnHa@gmail.com", LocalDate.of(1989, 03, 02), friend);
        Contact an2 = new Contact("An", "Ha", "84123098345", "AnHa@gmail.com", LocalDate.of(1989, 03, 02), friend);

        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        contacts.add(john);
        contacts.add(adam);
        contacts.add(jean);
        contacts.add(an);
        contacts.add(an2);

        return contacts;
    }

    public static ObservableList<Group> addGroupData() {

        ObservableList<Group> groupList = FXCollections.observableArrayList();
        groupList.add(family);
        groupList.add(friend);
        groupList.add(colleagues);

        return groupList;
    }
}
