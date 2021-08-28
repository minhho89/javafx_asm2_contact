package com.example.asm3.dao;

import com.example.asm3.controller.GroupController;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class ContactDAO {

    private static final File FILE = new File("./src/main/java/com/example/asm3/data/contacts.txt");

    static ObservableList<Contact> contacts;
    static ObservableList<Group> groups = GroupController.groups;

    public static ObservableList<Contact> loadContacts() throws IOException {
        contacts = FXCollections.observableArrayList();
        Scanner sc = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Contact contact = new Contact();
        try {
            sc = new Scanner(FILE);
            sc.useDelimiter(",,,");
            while(sc.hasNextLine()){
                contact.setFirstName(sc.next());
                contact.setLastName(sc.next());
                contact.setPhone(sc.next());
                contact.setEmail(sc.next());
                contact.setDob(LocalDate.parse(sc.next(), formatter));
                // find index group
                int index = GroupController.findIndexByGroupName(sc.next());
                if (index != -1) {
                    contact.setGroup(groups.get(index));
                } else {
                    contact.setGroup(null);
                }
                contacts.add(contact);
            }
        } finally {
          sc.close();
        }
        return contacts;
    }
}
