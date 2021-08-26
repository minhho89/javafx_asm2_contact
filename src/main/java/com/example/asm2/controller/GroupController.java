package com.example.asm2.controller;

import com.example.asm2.entity.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.example.asm2.util.DummyData.addGroupData;


public class GroupController {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private ListView<Group> groupListView;

    @FXML
    private TextField groupNameField;

    public void setContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    @FXML
    void initialize() {
        groupListView.setItems(addGroupData());
    }

    public String selectedGroup() {
        Group selectedGroup = groupListView.getSelectionModel().getSelectedItem();
        return selectedGroup.getName();
    }

    @FXML
    public void fillGroupName() {
        if (selectedGroup() != null) {
            groupNameField.setText(selectedGroup());
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close();

    }

    //search action
    public void searchAction() {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //add new group action
    public void addAction() throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //update group name
    public void updateAction() {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //delete a group, delete failed if there are some contact is in deleted one
    public void deleteAction() throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //operations on each button on window
    public void groupAction(ActionEvent evt) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //output all groups to table view
//    @FXML
//    public void showGroup(ObservableList<Group> groups) {
//
//
//    }
}
