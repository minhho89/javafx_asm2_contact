package com.example.asm3.controller;

import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;


public class UpdateContactController {

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

    @FXML
    void initialize() {

    }

    public void setContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    public void setContacts(List<Contact> contacts) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    public void setUpdatedContact(Contact updatedContact) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    public void updateContact(Contact contact) {
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        birthdayPicker.setValue(contact.getDob());
        groupCombo.setValue(contact.getGroup());
    }
}
