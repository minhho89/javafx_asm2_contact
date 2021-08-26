module com.example.asm2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;


    opens com.example.asm2.controller to javafx.fxml;
    exports com.example.asm2.controller;

    opens com.example.asm2 to javafx.graphics;
    exports com.example.asm2;

    opens com.example.asm2.entity to javafx.base;
    exports com.example.asm2.entity;
}