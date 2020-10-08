package com.example.danhbadienthoai.ui.main;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.service.MusicService;
import com.example.danhbadienthoai.ui.danhba.DanhbaActivity;
import com.example.danhbadienthoai.ui.music.MusicActivity;
import com.example.danhbadienthoai.ui.newsapp.NewsAppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    MainPresenter mainPresenter;
    @BindView(R.id.image_pause_playing_card)
    ImageView imgPausePlay;
    @BindView(R.id.image_song_music_playing_card)
    ImageView img_song_music;
    @BindView(R.id.text_author_song_playing_card)
    TextView txt_song_author;
    @BindView(R.id.text_title_song_playing_card)
    TextView txt_song_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, this);
        ButterKnife.bind(this);
        Glide.with(getApplicationContext()).load(MusicService.IMAGE_SONG)
                .placeholder(R.drawable.ic_music_background).into(img_song_music);

        txt_song_author.setText(MusicService.AUTHOR_SONG);
        txt_song_title.setText(MusicService.TITLE_SONG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MusicService.mediaPlayer.isPlaying()){
            imgPausePlay.setImageResource(R.drawable.ic_play);
        }else
        {
            imgPausePlay.setImageResource(R.drawable.ic_pause);
        }
        Glide.with(getApplicationContext()).load(MusicService.IMAGE_SONG)
                .placeholder(R.drawable.ic_music_background).into(img_song_music);
        txt_song_author.setText(MusicService.AUTHOR_SONG);
        txt_song_title.setText(MusicService.TITLE_SONG);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.image_pause_playing_card)
    void onClickPlay() {
        Intent intent = new Intent(MusicService.ACTION_PLAY, null, this, MusicService.class);
        startService(intent);
        if (MusicService.mediaPlayer.isPlaying()){
            imgPausePlay.setImageResource(R.drawable.ic_pause);
        }else
        {
           imgPausePlay.setImageResource(R.drawable.ic_play);
        }
    }

    @OnClick(R.id.button_next_playing_card)
    void onClickNext() {
        Intent intent = new Intent(MusicService.ACTION_NEXT, null, this, MusicService.class);
        startService(intent);
    }

    @OnClick(R.id.include_bar)
    void onClickIncludeBar() {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_list_music_playing_card)
    void onClickList() {

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