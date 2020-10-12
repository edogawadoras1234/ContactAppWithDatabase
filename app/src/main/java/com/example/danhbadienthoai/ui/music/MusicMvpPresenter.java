package com.example.danhbadienthoai.ui.music;

public interface MusicMvpPresenter extends MusicMvpView {
    void onTrackPrevious();

    void onTrackPlay();

    void onTrackPause();

    void onTrackNext();

    void onPlayMusic(String url);
}
