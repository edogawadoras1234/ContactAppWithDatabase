package com.example.danhbadienthoai.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.service.MusicService;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;
import com.example.danhbadienthoai.ui.music.MusicActivity;
import com.example.danhbadienthoai.ui.newsapp.NewsAppActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this, this);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.image_play_playing_card)
    void onClickPlay(){
        Intent intent = new Intent(MusicService.ACTION_PLAY, null, this, MusicService.class);
        startService(intent);
    }
    @OnClick(R.id.button_next_playing_card)
    void onClickNext(){
        Intent intent = new Intent(MusicService.ACTION_NEXT, null, this, MusicService.class);
        startService(intent);
    }

    @OnClick(R.id.include_bar)
    void onClickIncludeBar(){
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_list_music_playing_card)
    void onClickList(){
        Intent firstIntent = new Intent(this, MusicService.class);
        startService(firstIntent);
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
    void OnClickJpLanguage() {
        mainPresenter.onClickBtnJapanese();
    }

    @OnClick(R.id.image_vietnamese_language)
    void onClickVnLanguage() {
        mainPresenter.onClickBtnVietnamese();
    }

    @OnClick(R.id.button_app_music)
    void OnClickAppMusic() {
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