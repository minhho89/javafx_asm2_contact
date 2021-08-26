package com.example.asm2.controller;

import com.example.asm2.Main;
import com.example.asm2.entity.Contact;
import com.example.asm2.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.asm2.util.DummyData.addContactData;
import static com.example.asm2.util.DummyData.addGroupData;


public class ContactController {

    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    ObservableList<Group> groups = FXCollections.observableArrayList();

    @FXML
    private BorderPane mainPanel;

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
    public void showContact(List<Contact> c) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //output all groups to dropdownlist
    public void showGroup(List<Group> g) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //do corresponding actions for search, delete, update and add contact
    public void searchContact(ActionEvent evt) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");

    }

    //manage the groups
    public void groupPanel() throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");

    }

    @FXML
    public void showAddNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addContact.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(saveButton);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButton) {
            // Handle Save
        }
    }

    //update a contact
    @FXML
    public void showUpdateContactDialog() {
        // get selected contact
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No contact selected!");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to update");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Update a contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("updateContact.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(saveButton);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        UpdateContactController controller = fxmlLoader.getController();
        controller.updateContact(selectedContact);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButton) {
            // Handle Save
        }

    }

    //delete a selected contact
    @FXML
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

    @FXML
    public void openGroup() {
        Parent root;
        try {
            root = FXMLLoader.load(Main.class.getResource("group.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Groups Manager");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
