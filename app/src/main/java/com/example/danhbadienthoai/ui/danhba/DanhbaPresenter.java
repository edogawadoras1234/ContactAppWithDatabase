package com.example.danhbadienthoai.ui.danhba;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.danhbadienthoai.data.db.Database;
import com.example.danhbadienthoai.data.db.model.Contact;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.functions.Consumer;

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
        @SuppressLint("Recycle") Cursor cursor2 = danhbaActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (Objects.requireNonNull(cursor2).moveToNext()) {
            String id = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            String name = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String avatar = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            database.addData(id, name, phone, avatar, -1);
            Contact contact = new Contact(id, name, phone, avatar, -1);
            contactList.add(contact);
            danhbaMvpView.addData(id, name, phone, avatar);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onLoadData() {
        database = new Database(danhbaActivity);
        database.loadDataContact()
                .subscribe(contacts -> {
                    if (contacts.size() > 0) {
                        danhbaMvpView.showLoadDataSuccessed(contactList);
                    } else {
                        danhbaMvpView.showLoadDataFailed();
                    }
                });
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
