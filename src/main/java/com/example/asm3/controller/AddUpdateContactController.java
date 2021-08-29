package com.example.asm3.controller;

import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUpdateContactController {
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

    static ObservableList<Contact> contacts;
    static ObservableList<Group> groups = GroupController.groups;

    private static boolean emailFailureFlag;
    private static boolean phoneFailureFlag;
    private static boolean blankFieldFailureFlag;

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public AddUpdateContactController() {
        contacts = ContactController.contacts;
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

    public DialogPane getDialogPane() { return dialogPane;}

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    public VBox getVBox() {
        return vbox;
    }

    @FXML
    void initialize() {
        groupCombo.setItems(groups);
        resetAllFailureFlag();

        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !firstNameField.getText().isBlank()) {
                    fieldValidHandler(firstNameField);
                }
            }
        });

        lastNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !lastNameField.getText().isBlank()) {
                    fieldValidHandler(lastNameField);
                }
            }
        });

        phoneField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && phoneField.getText().isBlank() && checkPhoneFieldValidation(phoneField)) {
                    fieldValidHandler(phoneField);
                }
            }
        });

        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !emailField.getText().isBlank()) {
                    fieldValidHandler(emailField);
                }
            }
        });

        birthdayPicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && birthdayPicker.getValue() != null) {
                    fieldValidHandler(birthdayPicker);
                }
            }
        });

        groupCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && groupCombo.getValue() != null) {
                    fieldValidHandler(groupCombo);
                }
            }
        });
    }

    private void resetAllFailureFlag() {
        blankFieldFailureFlag = false;
        emailFailureFlag = false;
        phoneFailureFlag = false;
    }

    public void blankFieldsExistHandle() {
        if (!blankFieldFailureFlag) {
            Label message = new Label("* Please fill value to empty field(s)");
            message.setTextFill(Color.BROWN);
            vbox.getChildren().add(message);
            blankFieldFailureFlag = true;
        }
    }

    public boolean checkPhoneFieldValidation(TextField phoneField) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneField.getText());
        return matcher.matches();
    }

    public void phoneFieldValidationHandle() {
        if (!phoneFailureFlag) {
            Label message = new Label("* Phone field accepts only 10 digits input value");
            message.setTextFill(Color.BROWN);
            vbox.getChildren().add(message);
            phoneFailureFlag = true;
        }
    }

    public boolean checkEmailFieldValidation(TextField emailField) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailField.getText().matches(regex);
    }

    public void emailFieldValidationHandle() {
        if (!emailFailureFlag) {
            Label message = new Label("* The email address is not valid");
            message.setTextFill(Color.BROWN);
            vbox.getChildren().add(message);
            emailFailureFlag = true;
        }
    }



    private void addContact(Contact contact) {
        try {
            contacts.add(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    public void fieldValidHandler(Control control) {
        control.setStyle("-fx-border-color: grey;");
    }

    public void fieldInvalidHandle(Control control) {
        control.setStyle("-fx-border-color: #B22222;");
    }

    public void blankInvalidHandle() {
        if (getFirstNameField().getText().isBlank()){
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



    public void updateContact(Contact contact) {
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        phoneField.setText(contact.getPhone());
        emailField.setText(contact.getEmail());
        birthdayPicker.setValue(contact.getDob());
        groupCombo.setValue(contact.getGroup());
    }


}
