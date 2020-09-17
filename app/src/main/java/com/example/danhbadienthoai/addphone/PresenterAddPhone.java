package com.example.danhbadienthoai.addphone;

import com.example.danhbadienthoai.main.ViewMain;

public class PresenterAddPhone implements ViewAddPhone{

    ViewAddPhone viewAddPhone;
    ModelAddPhone modelAddPhone;

    public PresenterAddPhone(ViewAddPhone viewAddPhone){
        this.viewAddPhone = viewAddPhone;
    }
    public void received(String name, String phone, String avatar){
        modelAddPhone = new ModelAddPhone(this);
        modelAddPhone.addphone(name, phone, avatar);
    }

    @Override
    public void onFailed1() {
        viewAddPhone.onFailed1();
    }

    @Override
    public void onFailed2() {
        viewAddPhone.onFailed2();
    }

    @Override
    public void onSusscess() {
        viewAddPhone.onSusscess();
    }

    @Override
    public void cancle() {
        viewAddPhone.cancle();
    }
}
