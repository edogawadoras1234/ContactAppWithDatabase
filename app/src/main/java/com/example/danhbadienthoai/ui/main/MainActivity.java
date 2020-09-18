package com.example.danhbadienthoai.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.UngDungDocBao;
import com.example.danhbadienthoai.ui.danhba.danhba;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btnintocontact);
        Button btn_new = (Button) findViewById(R.id.btn_ungdung_docbao);

        mainPresenter = new MainPresenter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.onClickBtnContact();
            }
        });
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.onClickBtnNews();
            }
        });


    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void intoContact() {
        Intent intent = new Intent(MainActivity.this, danhba.class);
        startActivity(intent);
    }

    @Override
    public void intoNews() {
        Intent intent = new Intent(MainActivity.this, UngDungDocBao.class);
        startActivity(intent);
    }
}