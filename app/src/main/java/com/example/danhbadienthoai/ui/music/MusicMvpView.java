package com.example.danhbadienthoai.ui.music;

public interface MusicMvpView {
    void playMusic();

    void showIconPlay();

    void showIconPause();

    void showTimes(int mCurrentPosition, int mTotalDuration);

    void resetMediaPlayer();

    void startService();

    void destroyService();

    void seekBar();
}
