package com.example.danhbadienthoai.ui.countriesnews;

public class CountriesNewsPresenter implements CountriesNewsMvpPresenter {
    CountriesNewsMvpView countriesNewsMvpView;

    public CountriesNewsPresenter(CountriesNewsMvpView countriesNewsMvpView) {
        this.countriesNewsMvpView = countriesNewsMvpView;

    }

    @Override
    public void onLoadJson() {
        countriesNewsMvpView.loadJson();
    }

    @Override
    public void loadJson() {

    }
}
