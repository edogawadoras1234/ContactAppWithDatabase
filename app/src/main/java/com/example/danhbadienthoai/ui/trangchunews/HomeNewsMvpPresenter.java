package com.example.danhbadienthoai.ui.trangchunews;

public interface HomeNewsMvpPresenter extends  HomeNewsMvpView {
    void onLoadData(String q,  int limit, int offset);
    void onScrollData(String q,   int limit, int offset);
}
