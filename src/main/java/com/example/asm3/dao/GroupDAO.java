package com.example.asm3.dao;

import com.example.asm3.Main;
import com.example.asm3.entity.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GroupDAO {

    private static final File FILE = new File("./src/main/java/com/example/asm3/data/groups.txt");
    private static final String PATH = FILE.getAbsolutePath();

    static ObservableList<Group> groups;

    // Load all group to List
    public static ObservableList<Group> loadGroup() throws IOException {
        groups = FXCollections.observableArrayList();

        BufferedReader bw = null;
        try {
            bw = new BufferedReader(new FileReader(PATH));
            String groupName = "";
            while((groupName = bw.readLine()) != null) {
                groups.add(new Group(groupName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bw.close();
            return groups;
        }

    }

    //save all groups from a given list to a text file
    public static void saveGroupToFile() throws IOException {
        Writer wr = null;
        StringBuilder result;
        try {
            wr = new FileWriter(FILE);
            for (Group group : groups) {
                result = new StringBuilder(group.getName() + "\n");
                wr.write(result.toString());
            }
        } finally {
            wr.close();
        }

    }

}
