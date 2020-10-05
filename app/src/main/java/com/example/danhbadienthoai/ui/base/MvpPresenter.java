package com.example.danhbadienthoai.ui.base;

public interface MvpPresenter extends MvpView {
    void onLoadDataByCountry(String country,  int limit, int page);
    void onScrollDataByCountry(String country, int limit, int page);
}
