package com.example.danhbadienthoai.ui.music;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.service.MusicBroadcastReceiver;
import com.example.danhbadienthoai.service.MusicService;
import com.example.danhbadienthoai.utils.MusicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

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
    @BindView(R.id.image_music)
    ImageView img_music;
    @BindView(R.id.image_blur)
    ImageView img_blur;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        MusicBroadcastReceiver musicBroadcastReceiver = new MusicBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(musicBroadcastReceiver, filter);
        musicPresenter = new MusicPresenter(this, this);
        mRunnable.run();
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicPresenter.onInforSong();
        if (MusicService.mediaPlayer.isPlaying()) {
            img_play.setImageResource(R.drawable.ic_pause_blue);
        } else {
            img_play.setImageResource(R.drawable.ic_play_blue);
        }
    }

    @OnClick(R.id.button_suffer)
    void onClickBtnSuffer() {
        Toast.makeText(this, "onClickBtnSuffer", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_previous)
    void onClickBtnPrevious() {
        Intent intent = new Intent(MusicService.ACTION_PREVIOUS, null, this, MusicService.class);
        startService(intent);
        musicPresenter.onInforSong();
    }

    @OnClick(R.id.image_play)
    void onClickBtnPlay() {
        musicPresenter.onMusicPlay();
        musicPresenter.onIconPlay();
    }

    @OnClick(R.id.button_next)
    void onClickBtnNext() {
        Intent intent = new Intent(MusicService.ACTION_NEXT, null, this, MusicService.class);
        startService(intent);
        musicPresenter.onInforSong();
    }

    @OnClick(R.id.button_list_music)
    void onClickBtnList() {
        Toast.makeText(this, "onClickBtnList", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void playMusic() {

    }

    @Override
    public void showIconPlay() {
        if (MusicService.mediaPlayer.isPlaying()) {
            img_play.setImageResource(R.drawable.ic_play_blue);
        } else {
            img_play.setImageResource(R.drawable.ic_pause_blue);
        }
    }

    @Override
    public void showImage() {
        txt_song_author.setText(MusicService.AUTHOR_SONG);
        txt_song_title.setText(MusicService.TITLE_SONG);
        Glide.with(getApplicationContext()).load(MusicService.IMAGE_SONG)
                .placeholder(R.drawable.ic_music).into(img_music);

        Glide.with(this)
                .asBitmap()
                .load(MusicService.IMAGE_SONG) // or url
                .transform(new BlurTransformation(25, 3))
                .into(img_blur);
    }

    @Override
    public void seekBar() {

    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //set max value
            int mDuration = MusicService.mediaPlayer.getDuration();
            seekBar.setMax(mDuration);
            //update total time text view
            txt_duration.setText(MusicUtils.getTimeString(mDuration));
            //set progress to current position
            int mCurrentPosition = MusicService.mediaPlayer.getCurrentPosition();
            seekBar.setProgress(mCurrentPosition);
            //update current time text view
            txt_current_time.setText(MusicUtils.getTimeString(mCurrentPosition));
            //handle drag on seekbar
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        MusicService.mediaPlayer.seekTo(progress);
                    }
                }
            });
            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };
}