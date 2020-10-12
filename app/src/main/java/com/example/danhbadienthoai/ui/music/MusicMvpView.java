package com.example.danhbadienthoai.ui.music;

public interface MusicMvpView {
    void controlPrevious();

    void controlNext();

    void controlPause();

    void controlResume();

    void controlPlayMusic(String url);
}
