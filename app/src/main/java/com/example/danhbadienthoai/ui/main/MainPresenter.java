package com.example.danhbadienthoai.ui.main;


public class MainPresenter implements MainMvpPresenter {
    MainMvpView mainMvpView;

    public MainPresenter(MainMvpView mainMvpView) {
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
    public void intoContact() {

    }

    @Override
    public void intoNews() {

    }
}
