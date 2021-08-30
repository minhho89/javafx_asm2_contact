package com.example.asm3.controller;

import com.example.asm3.dao.ContactDAO;
import com.example.asm3.dao.GroupDAO;
import com.example.asm3.entity.Contact;
import com.example.asm3.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public static ObservableList<Group> groups;
    public static ObservableList<Group> searchGroups;

    static {
        try {
            groups = GroupDAO.loadGroup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private BorderPane mainPanel;
    @FXML
    private ListView<Group> groupListView;
    @FXML
    private TextField searchField;
    @FXML
    private TextField groupNameField;

    /**
     * Find index of a group in groupList by the group name
     *
     * @param groupName
     * @return -1 if not found
     */
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

    @FXML
    void initialize() {
        try {
            // Load group items from file
            groupListView.setItems(groups);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get selected group in list view
     *
     * @return selectedGroup
     */
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
        Stage thisStage = (Stage) mainPanel.getScene().getWindow();
        thisStage.close();
    }

    /**
     * Search groups by group name.
     * Searching matching name.
     *
     * @param groupName
     */
    // Search using linear algorithm
    public void search(String groupName) {
        searchGroups.removeAll();
        groupName = groupName.toLowerCase();
        for (Group group : groups) {
            if (group.getName().toLowerCase().contains(groupName)) {
                searchGroups.add(group);
            }
        }
    }

    @FXML
    public void searchAction() {
        searchGroups = FXCollections.observableArrayList();
        searchGroups.removeAll();

        if (!searchField.getText().isBlank()) {
            String searchingGroupName = searchField.getText();
            search(searchingGroupName);
            if (searchGroups.size() > 0) {
                groupListView.setItems(searchGroups);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No result found");
                alert.setHeaderText(null);
                alert.setContentText("No result found");
                alert.showAndWait();
            }
        } else {
            nameFieldBlankAlert();
        }
    }

    // Add new Group items to group
    @FXML
    public void addAction() throws IOException {
        if (!groupNameField.getText().isBlank()) {
            // Check duplicates
            if (!isBelongsToGroups(groupNameField.getText())) {
                // No duplicate -> add to group
                groups.add(new Group(groupNameField.getText()));

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Information");
                a.setHeaderText(null);
                a.setContentText("New Group Has Been Added Successfully");
                a.showAndWait();

                ContactController.groups = groups;
                GroupDAO.setGroups(groups);
                GroupDAO.saveGroupToFile();
            } else {
                // Inform user that input value duplicates
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Cannot add this group");
                a.setHeaderText(null);
                a.setContentText("Group " + groupNameField.getText() + " Is Already Added.");
                a.showAndWait();
            }
        } else {
            nameFieldBlankAlert();
        }
    }

    /**
     * Showing alert if name field is blank
     */
    private void nameFieldBlankAlert() {
        Alert a = new Alert((Alert.AlertType.ERROR));
        a.setTitle("Cannot perform task");
        a.setHeaderText(null);
        a.setContentText("Please input a group name.");
        a.showAndWait();
    }

    // Update group name
    @FXML
    public void updateAction() throws IOException {
        if (!groupNameField.getText().isBlank()) {
            Group selectedGroup = selectedGroup();
            if (selectedGroup != null) {
                String oldName = groups.get(groups.indexOf(selectedGroup)).getName();
                String newName = groupNameField.getText();

                if (isBelongsToGroups(newName)) {
                    // Check if newName equals to any existence group name or not
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cannot perform task");
                    alert.setHeaderText(null);
                    alert.setContentText("The group name \"" + newName + "\" is already exists. Please choose another name.");
                    alert.showAndWait();
                    return;
                }

                if (!oldName.equalsIgnoreCase(newName)) {
                    // old name is different from new name -> update
                    groups.get(groups.indexOf(selectedGroup)).setName(newName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Group \"" + oldName + "\" has been renamed to \"" + newName + "\" successfully.");
                    alert.showAndWait();

                    ObservableList<Contact> contacts = ContactController.getContacts();

                    for (Contact contact : contacts) {
                        if (contact.getGroup().getName().equalsIgnoreCase(oldName))
                            contact.getGroup().setName(newName);
                    }

                    ContactDAO.saveContactsToFile();

                    GroupDAO.setGroups(groups);
                    GroupDAO.saveGroupToFile();
                } else {
                    // old group name is same with new name
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Your new group name is the same with your old group name");
                    alert.showAndWait();
                }
            }
        } else {
            // field is blank
            nameFieldBlankAlert();
        }
    }

    @FXML
    public void deleteAction() throws IOException {
        Group selectedItem = selectedGroup();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delete group \"" + selectedItem + "\"");
        alert.setContentText("Please note that all contacts belong to this group will be deleted together.");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // remove contacts
            ContactController.getContacts().removeIf(contact -> contact.getGroup().equals(selectedItem));
            ContactDAO.saveContactsToFile();
            // remove group
            groups.remove(selectedItem);
            GroupDAO.setGroups(groups);
            GroupDAO.saveGroupToFile();

            return;
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        }

    }

    /**
     * Check if a group belong to the groupList
     * Checking by groupName
     *
     * @param groupName confirmation group name
     * @return true if found
     */
    private boolean isBelongsToGroups(String groupName) {
        if (groups != null) {
            for (Group group : groups) {
                if (group.getName().equalsIgnoreCase(groupName))
                    return true;
            }
        }
        return false;
    }
}
