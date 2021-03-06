package com.example.danhbadienthoai.utils;

import com.example.danhbadienthoai.data.db.model.Contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ContactUtils {
    public static final int VIEWTYPE_GROUP = 0;
    public static final int VIEWTYPE_CONTACT = 1;
    public static List<String> alphabet_available = new ArrayList<>();

    //sắp xếp list
    public static ArrayList<Contact> sortList(ArrayList<Contact> contacts){
        Collections.sort(contacts, (contact, t1) -> contact.getName().compareTo(t1.getName()));
        return contacts;
    }

    //Add chữ alpha lên đầu group
    public static ArrayList<Contact> addAlpha(ArrayList<Contact> contactArrayList) {
        int i;
        ArrayList<Contact> customList = new ArrayList<>();
        Contact firstContact = new Contact();
        firstContact.setName(String.valueOf(contactArrayList.get(0).getName().charAt(0)));
        firstContact.setViewType(VIEWTYPE_GROUP);
        alphabet_available.add(String.valueOf(contactArrayList.get(0).getName().charAt(0)));
        customList.add(firstContact);
        for (i = 0; i < contactArrayList.size() - 1; i++) {
            Contact contact = new Contact();
            char name1 = contactArrayList.get(i).getName().charAt(0);
            char name2 = contactArrayList.get(i + 1).getName().charAt(0);
            if (name1 == name2) {
                contactArrayList.get(i).setViewType(VIEWTYPE_CONTACT);
                customList.add(contactArrayList.get(i));
            } else {
                contactArrayList.get(i).setViewType(VIEWTYPE_CONTACT);
                customList.add(contactArrayList.get(i));
                contact.setName(String.valueOf(name2));
                contact.setViewType(VIEWTYPE_GROUP);
                alphabet_available.add(String.valueOf(name2));
                customList.add(contact);
            }
        }
        contactArrayList.get(i).setViewType(VIEWTYPE_CONTACT);
        customList.add(contactArrayList.get(i));
        return customList;
    }
}
