package com.example.asm3.controller;

import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

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
    private Label messageLabel;
    @FXML
    private Label messageLabel2;

    static ObservableList<Contact> contacts;
    static ObservableList<Group> groups = GroupController.groups;

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public AddUpdateContactController() {
        contacts = ContactController.contacts;
    }

    public Label getMessageLabel() {
        return messageLabel;
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

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    @FXML
    void initialize() {
        groupCombo.setItems(groups);
        messageLabel.setVisible(false);
        messageLabel2.setVisible(false);

        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !firstNameField.getText().isBlank()) {
                    blankFieldsFilledHandler(firstNameField);
                }
            }
        });

        lastNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !lastNameField.getText().isBlank()) {
                    blankFieldsFilledHandler(lastNameField);
                }
            }
        });

        phoneField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean) {
                    if (!phoneField.getText().isBlank()) {
                        blankFieldsFilledHandler(phoneField);
                    } else {
                        if (checkPhoneFieldValidation(phoneField)) {

                        };
                    }
                }
            }
        });

        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && !emailField.getText().isBlank()) {
                    blankFieldsFilledHandler(emailField);
                }
            }
        });

        birthdayPicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && birthdayPicker.getValue() != null) {
                    blankFieldsFilledHandler(birthdayPicker);
                }
            }
        });

        groupCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean && groupCombo.getValue() != null) {
                    blankFieldsFilledHandler(groupCombo);
                }
            }
        });
    }

    public boolean checkPhoneFieldValidation(TextField phoneField) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneField.getText());
        return matcher.matches();
    }

    public void phoneFieldValidationHandle() {
        messageLabel2.setVisible(true);
        messageLabel2.setText("* Phone field accepts only 10 digits input value");
    }

    public boolean checkEmailFieldValidation(TextField emailField) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneField.getText());
        return matcher.matches();
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

    public void blankFieldsFilledHandler(Control control) {
        control.setStyle("-fx-border-color: grey;");
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
