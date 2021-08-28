module com.example.asm2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;


    opens com.example.asm3.controller to javafx.fxml;
    exports com.example.asm3.controller;

    opens com.example.asm3 to javafx.graphics;
    exports com.example.asm3;

    opens com.example.asm3.entity to javafx.base;
    exports com.example.asm3.entity;
}