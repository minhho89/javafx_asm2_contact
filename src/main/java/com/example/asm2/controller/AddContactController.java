package com.example.asm2.controller;

import com.example.asm2.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import static com.example.asm2.util.DummyData.addGroupData;

public class AddContactController {

    ObservableList<Group> groups = FXCollections.observableArrayList();

//    private List<Contact> contacts;

//    public  void setContacts(List<Contact> contacts) {
//        this.contacts = contacts;
//    }
    @FXML
    private ComboBox<Group> groupCombo;

    public void setAddContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    @FXML
    void initialize() {
        groups = addGroupData();
        groupCombo.setItems(groups);
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
