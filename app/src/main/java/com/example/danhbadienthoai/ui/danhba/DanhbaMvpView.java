package com.example.danhbadienthoai.ui.danhba;

import android.database.Cursor;

public interface DanhbaMvpView {
    void showDiaglog();
    void showAddPhone();
    void addData(String id, String name, String phone, String avatar);
}
