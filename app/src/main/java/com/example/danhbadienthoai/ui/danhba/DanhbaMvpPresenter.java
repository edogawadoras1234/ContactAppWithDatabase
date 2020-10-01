package com.example.danhbadienthoai.ui.danhba;

import android.database.Cursor;

public interface DanhbaMvpPresenter extends DanhbaMvpView {
    void onClickMenuExit();

    void onClickMenuAddPhone();

    void onAddData();

    void onLoadData();
}
