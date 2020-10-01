package com.example.danhbadienthoai.ui.trangchunews;

public interface HomeNewsMvpPresenter extends  HomeNewsMvpView {
    void onLoadData(String q);
    void onScrollData(String q);
}
