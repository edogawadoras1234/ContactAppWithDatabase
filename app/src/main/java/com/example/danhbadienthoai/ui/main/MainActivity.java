package com.example.danhbadienthoai.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.music.MusicActivity;
import com.example.danhbadienthoai.ui.newsapp.NewsAppActivity;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this, this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_app_contact)
    void onClickAppContact() {
        mainPresenter.onClickBtnContact();
    }

    @OnClick(R.id.button_app_news)
    void onClickAppNews() {
        mainPresenter.onClickBtnNews();
    }

    @OnClick(R.id.image_japanese_language)
        void OnClickJpLanguage(){
        mainPresenter.onClickBtnJapanese();
    }
    @OnClick(R.id.image_vietnamese_language)
    void onClickVnLanguage(){
        mainPresenter.onClickBtnVietnamese();
    }
    @OnClick(R.id.button_app_music)
        void OnClickAppMusic(){
        mainPresenter.onClickBtnMusic();

    }

    @Override
    public void intoContact() {
        Intent intent = new Intent(MainActivity.this, DanhbaActivity.class);
        startActivity(intent);
    }

    @Override
    public void intoNews() {
        Intent intent = new Intent(MainActivity.this, NewsAppActivity.class);
        startActivity(intent);
    }

    @Override
    public void languageVietnamese() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);//load lại màn hình
        startActivity(intent);
    }

    @Override
    public void languageJapanese() {

    }

    @Override
    public void intoMusic() {
        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
        startActivity(intent);
    }
}