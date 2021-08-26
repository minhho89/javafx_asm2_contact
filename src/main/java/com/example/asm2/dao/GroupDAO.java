package com.example.asm2.dao;

import com.example.asm2.entity.Group;

import java.util.List;

public class GroupDAO {

    //load all groups from the file group in to a list
    public List<Group> loadGroup(String fname) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //save all groups from a given list to a text file
    public  void saveGroupToFile(List<Group> g, String fname) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //return the first position of a given contact group in the list
    //otherwise return -1
    public int indexOf(List<Group> list, Group g) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    //save a group to a current list
    public  void saveGroupToList(List<Group> list, Group g) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //return a list of Contact who information matched given search word
    public  List<Group> search(List<Group> c, String search) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //update a group in groups by a newGroup
    public  boolean updateGroup(List<Group> groups, int i, String oldGroup, String newGroup) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
}
