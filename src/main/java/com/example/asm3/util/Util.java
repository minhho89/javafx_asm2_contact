package com.example.asm3.util;

import javafx.scene.control.Alert;

public class Util {
    public static final String CONFIRM = "CONFIRMATION";

    public static Alert displayAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
