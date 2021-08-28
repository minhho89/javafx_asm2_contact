package com.example.asm3.controller;

import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public AddContactController() {
        contacts = ContactController.contacts;
    }

//    private static AddContactController instance;
//
//    public static AddContactController getInstance() {
//        if (instance == null) {
//            instance = new AddContactController();
//        }
//        return instance;
//    }


    public TextField getFirstNameField() {
        return firstNameField;
    }

    public void setFirstNameField(TextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public void setLastNameField(TextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public void setPhoneField(TextField phoneField) {
        this.phoneField = phoneField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public DatePicker getBirthdayPicker() {
        return birthdayPicker;
    }

    public void setBirthdayPicker(DatePicker birthdayPicker) {
        this.birthdayPicker = birthdayPicker;
    }

    public ComboBox<Group> getGroupCombo() {
        return groupCombo;
    }

    public void setGroupCombo(ComboBox<Group> groupCombo) {
        this.groupCombo = groupCombo;
    }

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
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

    /**
     * Checks if is there any field blank or not
     * A blank field is a field with null value or holds an empty String
     * @return true if blank, otherwise returns false
     */
    public boolean areAllFieldsBlank() {
        if (firstNameField.getText().isBlank() ||
        lastNameField.getText().isBlank() ||
        phoneField.getText().isBlank() ||
        emailField.getText().isBlank() ||
        birthdayPicker.toString().isBlank() ||
        groupCombo.getValue().toString().isBlank()) {
            return true;
        }
        return false;
    }

    public String findBlankField() {
        if (firstNameField.getText().isBlank()) return "First Name Field";
        if (lastNameField.getText().isBlank()) return "Last Name Field";
        if (phoneField.getText().isBlank()) return "Phone Field";
        if (emailField.getText().isBlank()) return "Email Field";
        if (birthdayPicker.toString().isBlank()) return "Birthday Field";
        if (groupCombo.getValue().toString().isBlank()) return "Group Selection Field";

        return "All fields are filled";
    }
}
