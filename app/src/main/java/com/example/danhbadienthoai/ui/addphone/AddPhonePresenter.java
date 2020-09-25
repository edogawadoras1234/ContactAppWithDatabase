package com.example.danhbadienthoai.ui.addphone;

import android.util.Log;

import com.example.danhbadienthoai.data.db.Database;

public class AddPhonePresenter implements AddPhoneMvpPresenter {

    AddPhoneMvpView addPhoneMvpView;
    AddPhoneActivity addPhoneActivity;
    Database database;

    public AddPhonePresenter(AddPhoneMvpView addPhoneMvpView, AddPhoneActivity addPhoneActivity) {
        this.addPhoneMvpView = addPhoneMvpView;
        this.addPhoneActivity = addPhoneActivity;
    }

    @Override
    public void onAddClick(String name, String phone, String avatar) {


        if (name.length() == 0 || phone.length() == 0) {

            addPhoneMvpView.Error();

        } else {

            database = new Database(addPhoneActivity);
            database.addData(null, name, phone, avatar, -1);
            addPhoneMvpView.Success();
            addPhoneMvpView.openMainActivity();
        }
    }

    @Override
    public void onCancleClick() {

        addPhoneMvpView.openMainActivity();
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void Error() {

    }

    @Override
    public void Success() {

    }
}
