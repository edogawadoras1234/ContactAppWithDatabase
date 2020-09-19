package com.example.danhbadienthoai.ui.main;

import android.util.Log;

public class MainPresenter implements MainMvpPresenter{
    MainMvpView mainMvpView;
    public MainPresenter(MainMvpView mainMvpView){
        this.mainMvpView = mainMvpView;
    }

    @Override
    public void onClickBtnContact() {
        mainMvpView.intoContact();
    }

    @Override
    public void onClickBtnNews() {
        mainMvpView.intoNews();
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void intoContact() {

    }

    @Override
    public void intoNews() {

    }
}
