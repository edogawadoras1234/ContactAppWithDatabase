package com.example.danhbadienthoai.ui.music;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class MusicPresenter implements MusicMvpPresenter {
    MediaPlayer mediaPlayer;
    MusicMvpView musicMvpView;

    public MusicPresenter(MusicMvpView musicMvpView) {
        this.musicMvpView = musicMvpView;
    }

    @Override
    public void onMusicPlay(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            musicMvpView.playMusic();
            musicMvpView.startService();
        });
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            Log.i("Music Presenter", "Error: " + e);
        }
    }

    @Override
    public void onIconPlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            musicMvpView.showIconPlay();
            musicMvpView.destroyService();
        } else {
            mediaPlayer.start();
            musicMvpView.startService();
            musicMvpView.showIconPause();
        }
    }

    @Override
    public void onShowTimes() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mediaPlayer != null) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                int mDuration = mediaPlayer.getDuration();
                musicMvpView.showTimes(mCurrentPosition, mDuration);
                Log.i("Musicaaaaaaaaaaa", "mDuration: " + mDuration + "  Current: " + mCurrentPosition);
            }
        }, 10);
    }

    @Override
    public void onResetMedia() {
        mediaPlayer.reset();
        musicMvpView.resetMediaPlayer();
    }

    @Override
    public void onSeekBar(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
            musicMvpView.seekBar();
        }
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
    public void showTimes(int mCurrentPosition, int mTotalDuration) {

    }

    @Override
    public void resetMediaPlayer() {

    }

    @Override
    public void startService() {

    }

    @Override
    public void destroyService() {

    }

    @Override
    public void seekBar() {

    }
}
