package com.example.danhbadienthoai.ui.music;

public interface MusicMvpPresenter extends MusicMvpView{
    void onMusicPlay(String url);
    void onIconPlay();
    void onShowTimes();
    void onResetMedia();
    void onSeekBar(int progress);
}
