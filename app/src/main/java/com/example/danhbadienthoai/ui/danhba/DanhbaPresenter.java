package com.example.danhbadienthoai.ui.danhba;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.adapter.ContactAdapter;
import com.example.danhbadienthoai.data.db.Database;
import com.example.danhbadienthoai.model.Contact;
import com.example.danhbadienthoai.utils.Common;

import java.util.ArrayList;

public class DanhbaPresenter implements DanhbaMvpPresenter {

    DanhbaMvpView danhbaMvpView;
    DanhbaActivity danhbaActivity;
    Database database;

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
        ArrayList contactList = new ArrayList<>();
        database = new Database(danhbaActivity);
        Cursor cursor = danhbaActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String avatar = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            Contact contact = new Contact(id, name, phone, avatar, -1);
            danhbaMvpView.addData(id, name, phone, avatar);
            database.addData(id, name, phone, avatar, -1);
            contactList.add(contact);
        }
    }

    @Override
    public void onLoadData() {
        database = new Database(danhbaActivity);
        ArrayList<Contact> contactArrayList;
        contactArrayList = new ArrayList<>();
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
          danhbaMvpView.showLoadDataFailed();
        } else {
            while (cursor.moveToNext()) {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
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
