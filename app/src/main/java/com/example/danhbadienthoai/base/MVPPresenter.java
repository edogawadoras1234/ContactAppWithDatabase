package com.example.danhbadienthoai.base;

public interface MVPPresenter <V extends MvpView> {
    void onAttach(V mvpView);
}
