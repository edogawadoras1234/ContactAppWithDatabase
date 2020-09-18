//package com.example.danhbadienthoai.ui.main;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.danhbadienthoai.R;
//
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainMvpView {
//
//    MainMvpPresenter mainMvpPresenter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        findViewIds();
//    }
//
//    public void findViewIds(){
//        Button button = (Button) findViewById(R.id.btnintocontact);
//        Button btn_new = (Button) findViewById(R.id.btn_ungdung_docbao);
//        button.setOnClickListener(this);
//        btn_new.setOnClickListener(this);
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnintocontact:
//             //   mainMvpPresenter.onBtnContact();
//                break;
//            case R.id.btn_ungdung_docbao:
//            //    mainMvpPresenter.onBtnNews();
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void openMainActivity() {
//
//    }
//}