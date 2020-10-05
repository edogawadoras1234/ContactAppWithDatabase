package com.example.danhbadienthoai.ui.japanesenews;

import com.example.danhbadienthoai.ui.base.MvpPresenter;

public interface JapaneseNewsMvpPresenter extends MvpPresenter, JapaneseNewsMvpView {
    @Override
    void onLoadDataByCountry(String country, int limit, int page);

    @Override
    void onScrollDataByCountry(String country, int limit, int page);
}
