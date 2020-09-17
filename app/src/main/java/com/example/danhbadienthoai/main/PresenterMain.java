package com.example.danhbadienthoai.main;

public class PresenterMain implements ViewMain{

    ViewMain viewMain;
    public PresenterMain(ViewMain viewMain){
        this.viewMain = viewMain;
    }

    @Override
    public void onBtnContact() {
     viewMain.onBtnContact();
    }

    @Override
    public void onBtnNews() {
        viewMain.onBtnNews();
    }
}
