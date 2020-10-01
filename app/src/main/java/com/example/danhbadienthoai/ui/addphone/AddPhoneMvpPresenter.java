package com.example.danhbadienthoai.ui.addphone;

public interface AddPhoneMvpPresenter extends AddPhoneMvpView {
    void onAddClick(String name, String phone, String avatar);

    void onCancleClick();
}
