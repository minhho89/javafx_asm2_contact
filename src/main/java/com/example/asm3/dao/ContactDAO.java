package com.example.asm3.dao;

import com.example.asm3.entity.Contact;

import java.util.List;


public class ContactDAO {

    //load all Contacts from the file Contact in to a list
    public List<Contact> loadContact(String fname) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //save all Contacts from a given list to a text file
    public void saveToFile(List<Contact> g, String fname) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //return the first position of a given contact g in the list
    //otherwise return -1
    public int indexOf(List<Contact> list, Contact g) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //save a Contact to a current list
    public void saveToList(List<Contact> list, Contact g) {
        list.add(g);
    }

    //update information of a contact c at position i in the list
    public void updateContact(List<Contact> list, Contact c, int i) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //return a list of Contact who information matched given search word
    public List<Contact> search(List<Contact> c, String group, String search) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //return a list of Contact who is in a given group
    public List<Contact> contactByGroup(List<Contact> c, String group) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
}
