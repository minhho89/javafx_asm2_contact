package com.example.asm3.controller;

import com.example.asm3.dao.GroupDAO;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.FXCollections;
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


    public static ObservableList<Group> groups;
    public static ObservableList<Group> searchGroups;
    static ObservableList<Contact> contacts;

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

    // TODO: move to DAO
    // Search using linear algorithm
    public int search(String groupName) {
        int index = 0;
        for (Group group : groups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
               return index;
            }
            index++;
        }
        return -1;
    }

    @FXML
    public void searchAction() {
        searchGroups = FXCollections.observableArrayList();
        searchGroups.removeAll();

        if (!searchField.getText().isBlank()) {
            String searchingGroupName = searchField.getText();
            int index = search(searchingGroupName);
            if (index != -1) {
                // If found
                searchGroups.add(groups.get(index));
                groupListView.setItems(searchGroups);
            } else {
                // If not
            }
        } else {
            groupListView.setItems(groups);
        }

    }

    // Add new Group items to group
    @FXML
    public void addAction()  {
        if (groupNameField.getText() != null || groupNameField.getText().trim() != "") {
            // Check duplicates
            if (search(groupNameField.getText()) == -1) {
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
        if(!belongToGroup(selectedItem)) {
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


    private boolean belongToGroup(Group CheckingGroup) {
        if (contacts != null) {
            for (Contact contact : contacts) {
                if (contact.getGroup().equals(CheckingGroup)) return true;
            }
        }
        return false;
    }

    public static int findIndexByGroupName(String groupName) {
        int i = 0;
        for (Group group : groups) {
            if (groupName.equalsIgnoreCase(group.getName())) {
                return i;
            }
            i++;
        }
        return -1;
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
