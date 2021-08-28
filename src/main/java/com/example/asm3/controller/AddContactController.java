package com.example.asm3.controller;

import com.example.asm3.dao.ContactDAO;
import com.example.asm3.dao.GroupDAO;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddContactController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker birthdayPicker;
    @FXML
    private ComboBox<Group> groupCombo;

    static ObservableList<Contact> contacts;
    static ObservableList<Group> groups = GroupController.groups;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public AddContactController() {
        contacts = ContactController.contacts;
    }

    private static AddContactController instance;

    public static AddContactController getInstance() {
        if (instance == null) {
            instance = new AddContactController();
        }
        return instance;
    }

    @FXML
    void initialize() {
        groupCombo.setItems(groups);
    }

    private void addContact(Contact contact) {
        try {
            contacts.add(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public Contact getNewContact() {
        Contact contact = new Contact();
        contact.setFirstName(firstNameField.getText());
        contact.setLastName(lastNameField.getText());
        contact.setPhone(phoneField.getText());
        contact.setEmail(emailField.getText());
        contact.setDob(birthdayPicker.getValue());
        contact.setGroup(groupCombo.getValue());

        return contact;
    }

}
