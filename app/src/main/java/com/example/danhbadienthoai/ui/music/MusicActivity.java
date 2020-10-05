package com.example.danhbadienthoai.ui.music;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.utils.MusicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MusicActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
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

        mediaPlayer("https://data25.chiasenhac.com/download2/2119/1/2118057-1a95e408/128/Hoa%20Hai%20Duong%20-%20Jack.mp3");
        txt_song_title.setText("Hoa Hải Đường");
        txt_song_author.setText("Jack");
        if (mediaPlayer.isPlaying()) {
            img_play.setImageResource(R.drawable.ic_play);
        } else {
            img_play.setImageResource(R.drawable.ic_pause);
        }

    }

    @OnClick(R.id.button_suffer)
    void onClickBtnSuffer() {
        Toast.makeText(this, "onClickBtnSuffer", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_previous)
    void onClickBtnPrevious() {
        mediaPlayer.reset();
        String url = "https://data18.chiasenhac.com/downloads/2006/1/2005502-717a19ce/128/Bac%20Phan%20-%20Jack_%20K-ICM.mp3";
        mediaPlayer(url);
        txt_song_title.setText("Bạc Phận");
        txt_song_author.setText("Jack");
    }

    @OnClick(R.id.image_play)
    void onClickBtnPlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            img_play.setImageResource(R.drawable.ic_play);
        } else {
            mediaPlayer.start();
            img_play.setImageResource(R.drawable.ic_pause);
        }
    }

    @OnClick(R.id.button_next)
    void onClickBtnNext() {
        mediaPlayer.reset();
        mediaPlayer("https://data2.chiasenhac.com/stream2/1727/1/1726451-d9a03b01/128/Mot%20Dieu%20-%20Wowy.mp3");
        txt_song_title.setText("Một điếu");
        txt_song_author.setText("Wowy");
    }

    @OnClick(R.id.button_list_music)
    void onClickBtnList() {
        Toast.makeText(this, "onClickBtnList", Toast.LENGTH_SHORT).show();
    }

    public void mediaPlayer(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mRunnable.run();
            }
        });
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                //set max value
                int mDuration = mediaPlayer.getDuration();
                seekBar.setMax(mDuration);
                //update total time text view
                txt_duration.setText(MusicUtils.getTimeString(mDuration));
                //set progress to current position
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
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
                        if (mediaPlayer != null && fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }
                });
            }
            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };
}
