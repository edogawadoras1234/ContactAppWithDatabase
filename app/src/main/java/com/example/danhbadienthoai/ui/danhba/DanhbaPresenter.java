package com.example.danhbadienthoai.ui.danhba;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.danhbadienthoai.data.db.Database;
import com.example.danhbadienthoai.data.db.model.Contact;
import com.example.danhbadienthoai.utils.ContactUtils;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DanhbaPresenter implements DanhbaMvpPresenter {

    DanhbaMvpView danhbaMvpView;
    DanhbaActivity danhbaActivity;
    Database database;
    ArrayList<Contact> contactList;

    public DanhbaPresenter(DanhbaMvpView danhbaMvpView, DanhbaActivity danhbaActivity) {
        this.danhbaMvpView = danhbaMvpView;
        this.danhbaActivity = danhbaActivity;
    }

    @Override
    public void onClickMenuExit() {
        danhbaMvpView.showDiaglog();
    }

    @Override
    public void onClickMenuAddPhone() {
        danhbaMvpView.showAddPhone();
    }

    @Override
    public void onAddData() {
        contactList = new ArrayList<>();
        database = new Database(danhbaActivity);
        Cursor cursor2 = danhbaActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor2.moveToNext()) {
            String id = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            String name = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String avatar = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
//            database.addData(id, name, phone, avatar, -1);
            Contact contact = new Contact(id, name, phone, avatar, -1);
            contactList.add(contact);
            danhbaMvpView.addData(id, name, phone, avatar);
        }

    }

    @Override
    public void onLoadData() {
        contactList = new ArrayList<>();
       danhbaMvpView.showLoadDataSuccessed(contactList);
        database = new Database(danhbaActivity);
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList = new ArrayList<>();
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            danhbaMvpView.showLoadDataFailed();
            Toast.makeText(danhbaActivity, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
                contactArrayList.add(contact);
             danhbaMvpView.showLoadDataSuccessed(contactArrayList);
            }
        }
    }

    @Override
    public void showDiaglog() {

    }

    @Override
    public void showAddPhone() {

    }

    @Override
    public void showLoadDataSuccessed(ArrayList<Contact> arrayList) {

    }

    @Override
    public void showLoadDataFailed() {

    }


    @Override
    public void addData(String id, String name, String phone, String avatar) {

    }
}
