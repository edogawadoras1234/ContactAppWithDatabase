package com.example.danhbadienthoai.ui.danhba;

import com.example.danhbadienthoai.data.db.model.Contact;

import java.util.ArrayList;

public interface DanhbaMvpView {
    void showDiaglog();

    void showAddPhone();

    void showLoadDataSuccessed(ArrayList<Contact> arrayList);

    void showLoadDataFailed();

    void addData(String id, String name, String phone, String avatar);
}
