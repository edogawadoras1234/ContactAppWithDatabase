package com.example.danhbadienthoai.addphone;

public class ModelAddPhone {

    ViewAddPhone viewAddPhone;
    public ModelAddPhone(ViewAddPhone viewAddPhone){
        this.viewAddPhone = viewAddPhone;
    }
    public void addphone(String name, String phone, String avatar) {
        if (name.length() == 0 && phone.length() == 0) {
                viewAddPhone.onFailed1();
            } else if (phone.length() == 0 || phone.length() == 0) {
                viewAddPhone.onFailed2();
            } else {
                viewAddPhone.onSusscess();
        }
    }
}
