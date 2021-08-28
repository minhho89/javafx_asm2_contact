package com.example.asm3.controller;

import com.example.asm3.dao.GroupDAO;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


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
    private static ObservableList<Contact> contacts;

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

    public Group selectedGroup() {
        Group selectedGroup = groupListView.getSelectionModel().getSelectedItem();
        return selectedGroup;
    }

    @FXML
    public void fillGroupName() {
        if (selectedGroup() != null) {
            groupNameField.setText(selectedGroup().getName());
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
    @FXML
    public void updateAction() {
        if (groupNameField.getText() != null || groupNameField.getText().trim() != "") {
            Group selectedGroup = selectedGroup();
            if (selectedGroup != null) {
                String oldName = groups.get(groups.indexOf(selectedGroup)).getName();
                String newName = groupNameField.getText();
                if (!oldName.equalsIgnoreCase(newName)) {
                    // old name is different from new name
                    groups.get(groups.indexOf(selectedGroup)).setName(newName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Group \"" + oldName + "\" has been renamed to \"" + newName + "\" successfully.");
                    alert.showAndWait();
                } else {
                    // old group name is same with new name
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Your new group name is the same with your old group name");
                    alert.showAndWait();
                }
            }
        }
    }

    
    //delete a group, delete failed if there are some contact is in deleted one
    @FXML
    public void deleteAction()  {
        Group selectedItem = selectedGroup();
        System.out.println(selectedItem.getName());
        if(!checkGroupHasContacts(selectedItem)) {
            // delete
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Do you want to delete selected group");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                groups.remove(selectedItem);
                return;
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed to delete Group");
            alert.setHeaderText("The Group has some contact(s) in it");
            alert.setContentText("Consider remove selected group out of relevant contact(s) before deleting it");
            alert.showAndWait();
        }
    }

    private boolean checkGroupHasContacts(Group CheckingGroup) {
        for (Contact contact : contacts) {
            if (contact.getGroup().equals(CheckingGroup)) return true;
        }
        return false;
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
