package com.example.asm2.controller;

import com.example.asm2.entity.Contact;
import com.example.asm2.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;


public class ContactController {

    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    ObservableList<Group> groups = FXCollections.observableArrayList();

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private ComboBox<Group> cbGroup;

    @FXML
    void initialize() {
        // Add dummies data
        contacts = addContactData();
        groups = addGroupData();
        contactsTable.setItems(contacts);
        cbGroup.setItems(groups);
    }

    //output all contact to tblContact
    public  void showContact(List<Contact> c) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    //output all groups to dropdownlist
    public  void showGroup(List<Group> g) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    //do corresponding actions for search, delete, update and add contact
    public void searchContact(ActionEvent evt) throws Exception{
        throw new UnsupportedOperationException("Remove this line and implement your code here!");

    }
    //manage the groups
    public void groupPanel() throws Exception{
        throw new UnsupportedOperationException("Remove this line and implement your code here!");

    }

    //update a contact
    public  void updateContact()throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    //delete a selected contact
    public void deleteContact() {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("No Contact Selected");
            alert.setContentText("Please select the contact you want to delete");
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Do you want to delete selected contact");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete contact
            return;
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        }
    }

    private ObservableList<Contact> addContactData() {
        Contact john = new Contact("John", "Snow", "84987234123", "john@gmail.com", "12-31-1987", "Family");
        Contact adam = new Contact("Adam", "Smith", "84980720100", "adam.smith@gmail.com", "1900-07-22", "Friend");
        Contact jean = new Contact("Jean", "Tonogbanua", "85231678987", "jean@yahoo.com", "9-9-1993", "Family");
        Contact an = new Contact("An", "Ha", "84123098345", "AnHa@gmail.com", "0189-03-02", "Friend");
        Contact an2 = new Contact("An", "Ha", "84123098345", "AnHa@gmail.com", "0017-08-31", "Friend");

        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        contacts.add(john);
        contacts.add(adam);
        contacts.add(jean);
        contacts.add(an);
        contacts.add(an2);

        return contacts;
    }

    private ObservableList<Group> addGroupData() {
        Group family = new Group("Family");
        Group friend = new Group("Friend");
        Group colleagues = new Group("Colleagues");

        ObservableList<Group> groupList = FXCollections.observableArrayList();
        groupList.add(family);
        groupList.add(friend);
        groupList.add(colleagues);

        return groupList;
    }


}
