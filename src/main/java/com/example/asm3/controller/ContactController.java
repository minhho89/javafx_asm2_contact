package com.example.asm3.controller;

import com.example.asm3.Main;
import com.example.asm3.dao.ContactDAO;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ContactController {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private ComboBox<Group> cbGroup;

    private ObservableList<Group> groups = GroupController.groups;

    public static ObservableList<Contact> contacts;
    static {
        try {
            contacts = ContactDAO.loadContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        contactsTable.setItems(contacts);
        cbGroup.setItems(groups);
    }

    @FXML
    public void showAddNewContactDialog() throws IOException {
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

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(saveButtonType);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        final Button btSave = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        System.out.println(btSave);

        AddContactController addController = fxmlLoader.getController();
        System.out.println(addController);

        // Handle input validation
        btSave.addEventFilter(ActionEvent.ACTION, event -> {
            if (addController.areAllFieldsBlank()) {
                event.consume();
                inValidHandle(addController);
            }
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
//             Check if any fields empty
            if (!addController.areAllFieldsBlank()) {
                Contact newContact = addController.getNewContact();
                contacts.add(newContact);
                // Save to file
                ContactDAO.saveContactsToFile();
            }
        }
    }

    /**
     * Temporally saves fields input value into a String array
     * Unfilled value will be left to null
     * @param addController
     * @return result array
     */
    private String[] saveTempInputValue(AddContactController addController) {
        String firstName = null;
        String lastName = null;
        String phone = null;
        String email = null;
        String dob = null;
        String group = null;
        String s = (!addController.getFirstNameField().getText().isBlank()) ?
                firstName = addController.getFirstNameField().getText() : null;
        s = (!addController.getLastNameField().getText().isBlank())?
            lastName = addController.getLastNameField().getText() : null;
        s = (!addController.getPhoneField().getText().isBlank())?
            phone = addController.getPhoneField().getText() : null;
        s = (!addController.getEmailField().getText().isBlank())?
            email = addController.getEmailField().getText() : null;
        s =(addController.getBirthdayPicker().getValue() != null)?
            dob = addController.getBirthdayPicker().getValue().toString() : null;
        s = (addController.getGroupCombo().getValue() != null)?
            group = addController.getGroupCombo().getValue().toString() : null;

        return new String[]{firstName, lastName, phone, email, dob, group};
    }

    private void inValidHandle(AddContactController addController) {
        if (addController.getFirstNameField().getText().isBlank()){
            addController.getFirstNameField().setStyle("-fx-text-box-border: #B22222;");
        }
        if (addController.getLastNameField().getText().isBlank()) {
            addController.getLastNameField().setStyle("-fx-text-box-border: #B22222;");
        }
        if (addController.getPhoneField().getText().isBlank()) {
            addController.getPhoneField().setStyle("-fx-text-box-border: #B22222;");
        }
        if (addController.getEmailField().getText().isBlank()) {
            addController.getEmailField().setStyle("-fx-text-box-border: #B22222;");
        }
        if (addController.getBirthdayPicker().getValue() == null) {
            addController.getBirthdayPicker().setStyle("-fx-border-color: #B22222;");
        }
        if (addController.getGroupCombo().getValue() == null) {
            addController.getGroupCombo().setStyle("-fx-border-color: #B22222;");
        }
        addController.getMessageLabel().setVisible(true);
    }

    private void fillTempInputValue(String[] inputValue, AddContactController addController) {
        addController.getFirstNameField().setText(inputValue[0]);
        addController.getLastNameField().setText(inputValue[1]);
        addController.getPhoneField().setText(inputValue[2]);
        addController.getEmailField().setText(inputValue[3]);
        addController.getBirthdayPicker().setValue(LocalDate.parse(inputValue[4],addController.getFormatter()));
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
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
