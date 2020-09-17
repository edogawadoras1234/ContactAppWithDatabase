package com.example.danhbadienthoai.main;

public class ModelMain {
    InterfaceMain interfaceMain;
    public ModelMain (InterfaceMain interfaceMain){
        this.interfaceMain = interfaceMain;
    }
    public void handlenewintent(){
        interfaceMain.onBtnContact();
        interfaceMain.onBtnNews();
    }
}
