package com.example.asm3.controller;

import com.example.asm3.dao.GroupDAO;
import com.example.asm3.entity.Group;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class GroupController {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private ListView<Group> groupListView;

    @FXML
    private TextField searchField;

    @FXML
    private TextField groupNameField;

    public void setContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    private static ObservableList<Group> groups;

    static {
        try {
            groups = GroupDAO.loadGroup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            // Load group items from file
            groupListView.setItems(groups);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    // Search using linear algorithm
    public boolean search(String groupName) {
        for (Group group : groups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void searchAction() {
        System.out.println(search(searchField.getText()));
    }

    // Add new Group items to group
    @FXML
    public void addAction()  {
        if (groupNameField.getText() != null || groupNameField.getText().trim() != "") {
            // Check duplicates
            if (!search(groupNameField.getText())) {
                // No duplicate -> add to group
                groups.add(new Group(groupNameField.getText()));
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Information");
                a.setHeaderText(null);
                a.setContentText("New Group Has Been Added Successfully");
                a.showAndWait();
            } else {
                // Inform user that input value duplicates
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Cannot add this group");
                a.setHeaderText(null);
                a.setContentText("Group " + groupNameField.getText() + " Is Already Added.");
                a.showAndWait();
            }
        }
    }

    // Update group name
    public void updateAction() {
        if (groupNameField.getText() != null || groupNameField.getText().trim() != "") {

        }
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
