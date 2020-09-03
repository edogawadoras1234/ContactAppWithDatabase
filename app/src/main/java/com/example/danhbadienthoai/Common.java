package com.example.danhbadienthoai;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Common {
    public static final int VIEWTYPE_GROUP = 0;
    public static final int VIEWTYPE_CONTACT = 1;
    public static final int RESULT_CODE = 1000;
    private static Database database;
    public static Context context;
    public static List<String> alphabet_available =  new ArrayList<>();

    public static ArrayList<Contact> sortList(ArrayList<Contact> contacts){
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getName().compareTo(t1.getName());
            }
        });
        return contacts;
    }
    public static ArrayList<Contact> addAlpha (ArrayList<Contact>contactArrayList){
        int i=0;
        ArrayList<Contact> customList = new ArrayList<>();
        Contact firstContact = new Contact();
        firstContact.setName(String.valueOf(contactArrayList.get(0).getName().charAt(0)));
        firstContact.setViewType(VIEWTYPE_GROUP);
        alphabet_available.add(String.valueOf(contactArrayList.get(0).getName().charAt(0)));

        customList.add(firstContact);
        for (i=0; i < contactArrayList.size()-1; i++){
            Contact contact = new Contact();
            char name1 = contactArrayList.get(i).getName().charAt(0);
            char name2 = contactArrayList.get(i+1).getName().charAt(0);
            if(name1 == name2){
                contactArrayList.get(i).setViewType(VIEWTYPE_CONTACT);
                customList.add(contactArrayList.get(i));
            }
            else{
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

    public static int findPosiWithName(String name, ArrayList<Contact> list){
        for (int i = 0; i< list.size();i++){
            if(list.get(i).getName().equals(name));
            return i;
        }
        return -1;
    }
    public static ArrayList<String> genAlpha() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 65; i < 90; i++) {
            char character = (char) i;
            result.add(String.valueOf(character));
        }
        return result;

    }
}
