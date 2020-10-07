package com.example.danhbadienthoai.ui.music;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.service.MusicBroadcastReceiver;
import com.example.danhbadienthoai.service.MusicService;
import com.example.danhbadienthoai.utils.MusicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicActivity extends AppCompatActivity implements MusicMvpView {

    MusicPresenter musicPresenter;
    @BindView(R.id.text_current_time)
    TextView txt_current_time;
    @BindView(R.id.text_duration)
    TextView txt_duration;
    @BindView(R.id.text_song_title)
    TextView txt_song_title;
    @BindView(R.id.text_song_author)
    TextView txt_song_author;
    @BindView(R.id.image_play)
    ImageView img_play;
    @BindView(R.id.songProgressBar)
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);

        MusicBroadcastReceiver musicBroadcastReceiver = new MusicBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(musicBroadcastReceiver, filter);

        Intent firstIntent = new Intent(this, MusicService.class);
        startService(firstIntent);
//        musicPresenter = new MusicPresenter(this);
//        musicPresenter.onMusicPlay("http://www.infinityandroid.com/music/good_times.mp3");
//        musicPresenter.onIconPlay();
    }

    @OnClick(R.id.button_suffer)
    void onClickBtnSuffer() {
        Toast.makeText(this, "onClickBtnSuffer", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_previous)
    void onClickBtnPrevious() {
        Intent intent = new Intent(MusicService.ACTION_PREVIOUS, null, this, MusicService.class);
        startService(intent);
    }

    @OnClick(R.id.image_play)
    void onClickBtnPlay() {
        Intent intent = new Intent(MusicService.ACTION_PLAY, null, this, MusicService.class);
        startService(intent);
       // musicPresenter.onIconPlay();
    }

    @OnClick(R.id.button_next)
    void onClickBtnNext() {
        Intent intent = new Intent(MusicService.ACTION_NEXT, null, this, MusicService.class);
        startService(intent);
    }

    @OnClick(R.id.button_list_music)
    void onClickBtnList() {
        Toast.makeText(this, "onClickBtnList", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void playMusic() {
      //  musicPresenter.onShowTimes();
    }

    @Override
    public void showIconPlay() {
        img_play.setImageResource(R.drawable.ic_play);
    }

    @Override
    public void showIconPause() {
        img_play.setImageResource(R.drawable.ic_pause);
    }

    @Override
    public void showTimes(int mCurrentPosition, int mTotalDuration) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            txt_duration.setText(MusicUtils.getTimeString(mTotalDuration));
            seekBar.setMax(mTotalDuration);
            txt_current_time.setText(MusicUtils.getTimeString(mCurrentPosition));
            seekBar.setProgress(mCurrentPosition);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    musicPresenter.onSeekBar(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }, 10);
    }

    @Override
    public void resetMediaPlayer() {

    }

    @Override
    public void startService() {
        Intent firstIntent = new Intent(this, MusicService.class);
        startService(firstIntent);
    }

    @Override
    public void destroyService() {
        stopService(new Intent(this, MusicService.class));
    }

    @Override
    public void seekBar() {

    }
}