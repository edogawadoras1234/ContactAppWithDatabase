package com.example.danhbadienthoai.ui.music;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.example.danhbadienthoai.service.MusicService;

public class MusicPresenter implements MusicMvpPresenter {
    MusicMvpView musicMvpView;
    MusicActivity musicActivity;

    public MusicPresenter(MusicMvpView musicMvpView, MusicActivity musicActivity) {
        this.musicMvpView = musicMvpView;
        this.musicActivity = musicActivity;
    }

    @Override
    public void onMusicPlay() {
        Intent intent = new Intent(MusicService.ACTION_PLAY, null, musicActivity, MusicService.class);
        musicActivity.startService(intent);
        musicMvpView.playMusic();
    }

    @Override
    public void onIconPlay() {
        if (MusicService.mediaPlayer.isPlaying()) {
            musicMvpView.showIconPlay();
        } else {
            musicMvpView.showIconPause();
        }
    }

    @Override
    public void onSeekBar(int progress) {
        MusicService.mediaPlayer.seekTo(progress);
        musicMvpView.seekBar();
    }

    @Override
    public void playMusic() {

    }

    @Override
    public void showIconPlay() {

    }

    @Override
    public void showIconPause() {

    }

    @Override
    public void seekBar() {

    }
}