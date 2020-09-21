package com.example.danhbadienthoai.ui.addphone;
import android.util.Log;
import android.widget.Toast;

public class AddPhonePresenter implements AddPhoneMvpPresenter {

  AddPhoneMvpView addPhoneMvpView;

  public AddPhonePresenter(AddPhoneMvpView addPhoneMvpView){
    this.addPhoneMvpView = addPhoneMvpView;
  }
  @Override
  public void onAddClick(String name, String phone, String avatar) {

      if (name.length() == 0 || phone.length() == 0){

        addPhoneMvpView.Error();

      }else{

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
