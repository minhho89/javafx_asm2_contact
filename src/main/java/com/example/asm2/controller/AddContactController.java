package com.example.asm2.controller;

import com.example.asm2.entity.Contact;
import javafx.fxml.FXML;

import java.util.List;

public class AddContactController {

    public void  setAddContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    private List<Contact> contacts;

    public  void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @FXML
    void initialize() {

    }

    @FXML
    public void saveContact() {

    }
//    public  void saveContact(ActionEvent evt)throws  Exception {
//        throw new UnsupportedOperationException("Remove this line and implement your code here!");
//    }

    @FXML
    public void cancelButton_Click() {

    }
}
