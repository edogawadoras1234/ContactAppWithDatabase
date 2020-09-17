package com.example.danhbadienthoai.main;

public class PresenterMain implements InterfaceMain{

    ModelMain modelMain;
    ViewMain viewMain;
    public PresenterMain(ViewMain viewMain){
        this.viewMain = viewMain;
    }
    public  void nhantinhieu(){
        modelMain = new ModelMain(this);
        modelMain.handlenewintent();
    }

    @Override
    public void onBtnContact() {
     viewMain.onBtnContact();
    }

    @Override
    public void onBtnNews() {
        viewMain.onBtnnews();
    }
}
