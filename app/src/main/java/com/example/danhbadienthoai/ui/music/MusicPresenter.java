package com.example.danhbadienthoai.ui.music;

import android.content.Intent;

//import com.example.danhbadienthoai.service.MusicService;

public class MusicPresenter implements MusicMvpPresenter {
    MusicMvpView musicMvpView;
    MusicActivity musicActivity;

    public MusicPresenter(MusicMvpView musicMvpView, MusicActivity musicActivity) {
        this.musicMvpView = musicMvpView;
        this.musicActivity = musicActivity;
    }

    @Override
    public void onTrackPrevious() {
        musicMvpView.controlPrevious();
    }

    @Override
    public void onTrackPlay() {
        musicMvpView.controlResume();
    }

    @Override
    public void onTrackPause() {
        musicMvpView.controlPause();
    }

    @Override
    public void onTrackNext() {
        musicMvpView.controlNext();
    }

    @Override
    public void onPlayMusic(String url) {
        musicMvpView.controlPlayMusic(url);
    }

    @Override
    public void controlPrevious() {

    }

    @Override
    public void controlNext() {

    }

    @Override
    public void controlPause() {

    }

    @Override
    public void controlResume() {

    }

    @Override
    public void controlPlayMusic(String url) {

    }
}