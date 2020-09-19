package com.example.danhbadienthoai.ui.addphone;
import android.util.Log;

public class AddPhonePresenter implements AddPhoneMvpPresenter {

  AddPhoneMvpView addPhoneMvpView;

  public AddPhonePresenter(AddPhoneMvpView addPhoneMvpView){
    this.addPhoneMvpView = addPhoneMvpView;
  }
  @Override
  public void onAddClick(String name, String phone, String avatar) {

      if (name.length() == 0 || phone.length() == 0){
        addPhoneMvpView.Error();
        Log.i("aaaaaaaaaa","That bai");

      }else{
        Log.i("bbbbbbbbbbbbb","Thanh cong");
        addPhoneMvpView.Success();
        addPhoneMvpView.openMainActivity();
      }
  }

  @Override
  public void onCancleClick() {
    Log.i("cccccccccccccc","Cancle");
    addPhoneMvpView.openMainActivity();
  }

  @Override
  public void openMainActivity() {

  }

  @Override
  public void Error() {

  }

  @Override
  public void Success() {

  }
}
