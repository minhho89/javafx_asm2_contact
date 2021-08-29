package com.example.asm3.controller;

import com.example.asm3.Main;
import com.example.asm3.dao.ContactDAO;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
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
        fxmlLoader.setLocation(Main.class.getResource("addUpdateContact.fxml"));
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

        AddUpdateContactController addController = fxmlLoader.getController();

        // Handle input validation
        btSave.addEventFilter(ActionEvent.ACTION, event -> {
            // Check if all fields are blank
            if (addController.areAllFieldsBlank()) {
                event.consume();
                addController.blankInvalidHandle();
            } else {
                // valid handle
                for (Control control : addController.getControls()) {
                    if (control instanceof TextField) {
                        if (!((TextField) control).getText().isBlank()) {
                            addController.fieldValidHandle(control);
                        }
                    }
                    if (control instanceof DatePicker || control instanceof ComboBox) {
                        if(((DatePicker)control).getValue() != null) {
                            addController.fieldValidHandle(control);
                        }
                    }
                }
                addController.blankFieldsResolveHandle();
            }

            // Check if PhoneField is valid
            if (!addController.checkPhoneFieldValidation(addController.getPhoneField())) {
                event.consume();
                addController.fieldInvalidHandle(addController.getPhoneField());
                addController.phoneFieldInvalidationHandle();
            } else {
                // valid handle
                addController.phoneFieldValidationHandle();
                addController.fieldValidHandle(addController.getPhoneField());
            }

            // Check if Email field is valid
            if (!addController.checkEmailFieldValidation(addController.getEmailField())) {
                event.consume();
                addController.fieldInvalidHandle(addController.getEmailField());
                addController.emailFieldInvalidationHandle();
            } else {
                // valid handle
                addController.emailFieldValidationHandle();
                addController.fieldValidHandle(addController.getEmailField());
            }

            addController.getDialogPane().getScene().getWindow().sizeToScene(); // resize the dialog when children added
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
//             Check if any fields empty
            if (!addController.areAllFieldsBlank()) {
                Contact newContact = addController.getInputContact();
                contacts.add(newContact);
                // Save to file
                ContactDAO.saveContactsToFile();
            }
        }
    }

    //update a contact
    @FXML
    public void showUpdateContactDialog() throws IOException {
        // get selected contact
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();

        // If no contact selected then inform user
        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No contact selected!");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to update");
            alert.showAndWait();
            return;
        }

        // If contact selected, open dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Update a contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("addUpdateContact.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(saveButtonType);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        final Button btSave = (Button) dialog.getDialogPane().lookupButton(saveButtonType);

        AddUpdateContactController updateController = fxmlLoader.getController();
        updateController.updateContact(selectedContact);

        // Handle input validation
        btSave.addEventFilter(ActionEvent.ACTION, event -> {
            // Check if all fields are blank
            if (updateController.areAllFieldsBlank()) {
                event.consume();
                updateController.blankInvalidHandle();
            } else {
                // valid handle
                for (Control control : updateController.getControls()) {
                    if (control instanceof TextField) {
                        if (!((TextField) control).getText().isBlank()) {
                            updateController.fieldValidHandle(control);
                        }
                    }
                    if (control instanceof DatePicker || control instanceof ComboBox) {
                        if(((DatePicker)control).getValue() != null) {
                            updateController.fieldValidHandle(control);
                        }
                    }
                }
                updateController.blankFieldsResolveHandle();
            }

            // Check if PhoneField is valid
            if (!updateController.checkPhoneFieldValidation(updateController.getPhoneField())) {
                event.consume();
                updateController.fieldInvalidHandle(updateController.getPhoneField());
                updateController.phoneFieldInvalidationHandle();
            } else {
                // valid handle
                updateController.phoneFieldValidationHandle();
                updateController.fieldValidHandle(updateController.getPhoneField());
            }

            // Check if Email field is valid
            if (!updateController.checkEmailFieldValidation(updateController.getEmailField())) {
                event.consume();
                updateController.fieldInvalidHandle(updateController.getEmailField());
                updateController.emailFieldInvalidationHandle();
            } else {
                // valid handle
                updateController.emailFieldValidationHandle();
                updateController.fieldValidHandle(updateController.getEmailField());
            }

            updateController.getDialogPane().getScene().getWindow().sizeToScene(); // resize the dialog when children added
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            //Check if any fields empty
            if (!updateController.areAllFieldsBlank()) {
                // Get selected contact
                int index = contacts.indexOf(selectedContact);
                Contact updatedContact = updateController.getInputContact();
                contacts.set(index, updatedContact);
                System.out.println(selectedContact);
                // Save to file
                ContactDAO.saveContactsToFile();
            }
        }
    }

    //delete a selected contact
    @FXML
    public void deleteContact() throws IOException {
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
            contacts.remove(selectedContact);
            ContactDAO.saveContactsToFile();
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
