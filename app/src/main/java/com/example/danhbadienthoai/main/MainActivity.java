package com.example.danhbadienthoai.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.UngDungDocBao;
import com.example.danhbadienthoai.danhba;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewMain{

    PresenterMain presenterMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewIds();

        presenterMain = new PresenterMain(this);

    }

    @Override
    public void onBtnContact() {
                Intent intent = new Intent(MainActivity.this, danhba.class);
                startActivity(intent);
    }

    @Override
    public void onBtnnews() {
        Intent intent = new Intent(MainActivity.this, UngDungDocBao.class);
        startActivity(intent);
    }

    public void findViewIds(){
        Button button = (Button) findViewById(R.id.btnintocontact);
        Button btn_new = (Button) findViewById(R.id.btn_ungdung_docbao);
        button.setOnClickListener(this);
        btn_new.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnintocontact:
                presenterMain.onBtnContact();
                break;
            case R.id.btn_ungdung_docbao:
                presenterMain.onBtnNews();
                break;
            default:
                break;
        }
    }
}