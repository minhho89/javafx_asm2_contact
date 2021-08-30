package com.example.asm3.controller;

import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUpdateContactController {
    static ObservableList<Contact> contacts;
    static ObservableList<Group> groups = GroupController.groups;
    private static boolean emailFailureFlag;
    private static boolean phoneFailureFlag;
    private static boolean blankFieldFailureFlag;
    Label blankMessage = new Label();
    Label phoneMessage = new Label();
    Label emailMessage = new Label();
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
    private VBox vbox;
    @FXML
    private DialogPane dialogPane;
    private ObservableList<Control> controls;

    public AddUpdateContactController() {
        contacts = ContactController.getContacts();
        controls = FXCollections.observableArrayList(firstNameField, lastNameField, phoneField,
                emailField, birthdayPicker, groupCombo);
    }

    public ObservableList<Control> getControls() {
        return controls;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public DatePicker getBirthdayPicker() {
        return birthdayPicker;
    }

    public ComboBox<Group> getGroupCombo() {
        return groupCombo;
    }

    public DialogPane getDialogPane() {
        return dialogPane;
    }

    @FXML
    void initialize() {
        groupCombo.setItems(groups);
        resetAllFailureFlag();

        // Handles valid and invalid input to fields
        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !firstNameField.getText().isBlank()) {
                    fieldValidHandle(firstNameField);
                }
            }
        });

        lastNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !lastNameField.getText().isBlank()) {
                    fieldValidHandle(lastNameField);
                }
            }
        });

        phoneField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && phoneField.getText().isBlank() && checkPhoneFieldValidation(phoneField)) {
                    fieldValidHandle(phoneField);
                }
            }
        });

        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !emailField.getText().isBlank() && checkEmailFieldValidation(emailField)) {
                    fieldValidHandle(emailField);
                }
            }
        });

        birthdayPicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && birthdayPicker.getValue() != null) {
                    fieldValidHandle(birthdayPicker);
                }
            }
        });

        groupCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && groupCombo.getValue() != null) {
                    fieldValidHandle(groupCombo);
                }
            }
        });
    }


    /**
     * Get value from fields
     *
     * @return contact
     */
    public Contact getInputContact() {
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
     *
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

    /**
     * Handle field if input is valid
     * Set border color to grey
     *
     * @param control input field
     */
    public void fieldValidHandle(Control control) {
        control.setStyle("-fx-border-color: grey;");
    }

    /**
     * Handle field if input is invalid
     * Set border color to red
     *
     * @param control input field
     */
    public void fieldInvalidHandle(Control control) {
        control.setStyle("-fx-border-color: #B22222;");
    }

    /**
     * Handle field if user let blank
     */
    public void blankInvalidHandle() {
        if (getFirstNameField().getText().isBlank()) {
            fieldInvalidHandle(getFirstNameField());
        }
        if (getLastNameField().getText().isBlank()) {
            fieldInvalidHandle(getLastNameField());
        }
        if (getPhoneField().getText().isBlank()) {
            fieldInvalidHandle(getPhoneField());
        }
        if (getEmailField().getText().isBlank()) {
            fieldInvalidHandle(getEmailField());
        }
        if (getBirthdayPicker().getValue() == null) {
            fieldInvalidHandle(getBirthdayPicker());
        }
        if (getGroupCombo().getValue() == null) {
            fieldInvalidHandle(getGroupCombo());
        }
        blankFieldsExistHandle();
    }

    /**
     * Handle blank validity of all fields of an AddUpdateController instance
     */
    public void blankValidHandle() {
        for (Control control : controls) {
            if (control instanceof TextField) {
                if (!((TextField) control).getText().isBlank()) {
                    fieldValidHandle(control);
                }
            }
            if (control instanceof DatePicker || control instanceof ComboBox) {
                if(((DatePicker)control).getValue() != null) {
                    fieldValidHandle(control);
                }
            }
        }
    }

    /**
     * Populate input value into fields
     *
     * @param contact input field
     */
    public void updateContact(Contact contact) {
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        birthdayPicker.setValue(contact.getDob());
        groupCombo.setValue(contact.getGroup());
    }

    // Below code is handle each field's valid or invalid behavior

    private void resetAllFailureFlag() {
        blankFieldFailureFlag = false;
        emailFailureFlag = false;
        phoneFailureFlag = false;
    }

    public void blankFieldsExistHandle() {
        if (!blankFieldFailureFlag) {
            blankMessage.setText("* Please fill value to empty field(s)");
            blankMessage.setTextFill(Color.BROWN);
            vbox.getChildren().add(blankMessage);
            blankFieldFailureFlag = true;
        }
    }

    public void blankFieldsResolveHandle() {
        if (blankFieldFailureFlag) {
            try {
                vbox.getChildren().remove(blankMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkPhoneFieldValidation(TextField phoneField) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneField.getText());
        return matcher.matches();
    }

    public void phoneFieldInvalidationHandle() {
        if (!phoneFailureFlag) {
            phoneMessage.setText("* Phone field accepts only 10 digits input value");
            phoneMessage.setTextFill(Color.BROWN);
            vbox.getChildren().add(phoneMessage);
            phoneFailureFlag = true;
        }
    }

    public void phoneFieldValidationHandle() {
        if (phoneFailureFlag) {
            try {
                vbox.getChildren().remove(phoneMessage);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                phoneFailureFlag = false;
            }
        }
    }

    public boolean checkEmailFieldValidation(TextField emailField) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailField.getText().matches(regex);
    }

    public void emailFieldInvalidationHandle() {
        if (!emailFailureFlag) {
            emailMessage.setText("* The email address is not valid");
            emailMessage.setTextFill(Color.BROWN);
            vbox.getChildren().add(emailMessage);
            emailFailureFlag = true;
        }
    }

    public void emailFieldValidationHandle() {
        if (emailFailureFlag) {
            try {
                vbox.getChildren().remove(emailMessage);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                emailFailureFlag = false;
            }
        }
    }

}
