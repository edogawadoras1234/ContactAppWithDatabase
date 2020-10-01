package com.example.danhbadienthoai.ui.main;


import android.content.Intent;
import android.content.res.Configuration;

import java.util.Locale;

public class MainPresenter implements MainMvpPresenter {
    MainMvpView mainMvpView;
    MainActivity mainActivity;

    public MainPresenter(MainMvpView mainMvpView, MainActivity mainActivity) {
        this.mainMvpView = mainMvpView;
        this.mainActivity = mainActivity;
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
    public void onClickBtnJapanese() {
        mainMvpView.languageVietnamese();
        Locale locale = new Locale("ja");
        Configuration configuration = new Configuration();
        configuration.locale = locale; //cấu hình lại ngôn ngữ
        mainActivity.getBaseContext().getResources().updateConfiguration(
                configuration, mainActivity.getBaseContext().getResources().getDisplayMetrics()// cập nhật resource theo ngôn ngữ mình chọn
        );
        mainMvpView.languageJapanese();
    }

    @Override
    public void onClickBtnVietnamese() {
        mainMvpView.languageVietnamese();
        Locale locale = new Locale("vn");
        Configuration configuration = new Configuration();
        configuration.locale = locale; //cấu hình lại ngôn ngữ
        mainActivity.getBaseContext().getResources().updateConfiguration(
                configuration, mainActivity.getBaseContext().getResources().getDisplayMetrics()// cập nhật resource theo ngôn ngữ mình chọn
        );
        mainMvpView.languageVietnamese();
    }


    @Override
    public void intoContact() {

    }

    @Override
    public void intoNews() {

    }

    @Override
    public void languageVietnamese() {

    }

    @Override
    public void languageJapanese() {

    }
}
