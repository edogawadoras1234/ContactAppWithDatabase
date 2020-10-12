package com.example.danhbadienthoai.ui.music;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.danhbadienthoai.CreateNotification;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.Songs;
import com.example.danhbadienthoai.service.MusicBroadcastReceiver;
import com.example.danhbadienthoai.service.MusicService;
import com.example.danhbadienthoai.service.MusicService2;
import com.example.danhbadienthoai.ui.listmusic.ListMusicActivity;
import com.example.danhbadienthoai.utils.MusicUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @BindView(R.id.image_music)
    ImageView img_music;
    @BindView(R.id.image_blur)
    ImageView img_blur;

    String songName, songLocation, songAuthor;
    byte[] songImage;
    boolean isPlaying = false;
    int position = 0;
    List<Songs> songs = new ArrayList<>();
    NotificationManager notificationManager;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public List<Songs> getSongsFromListMusic()
    {
        return songs;
    }
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
        onLoadListMusic();
        Intent intent = getIntent();
        songName = intent.getStringExtra("songName");
        songLocation = intent.getStringExtra("songLocation");
        songAuthor = intent.getStringExtra("songAuthor");
        songImage = intent.getByteArrayExtra("songImage");

        createChannel();
        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        startService(new Intent(getBaseContext(), MusicService2.class));

        mRunnable.run();
        img_play.setImageResource(R.drawable.ic_play_blue);
    }

    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                "KOD Dev", NotificationManager.IMPORTANCE_LOW);

        notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = Objects.requireNonNull(intent.getExtras()).getString("actionname");

            assert action != null;
            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    musicPresenter.onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (isPlaying) {
                        musicPresenter.onTrackPause();
                    } else {
                        musicPresenter.onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    musicPresenter.onTrackNext();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.button_suffer)
    void onClickBtnSuffer() {
        Toast.makeText(this, "onClickBtnSuffer", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_previous)
    void onClickBtnPrevious() {
        musicPresenter.onTrackPrevious();
    }

    @OnClick(R.id.image_play)
    void onClickBtnPlay() {
        if (mediaPlayer.isPlaying()) {
            musicPresenter.onTrackPause();
        } else {
            musicPresenter.onTrackPlay();
        }
    }

    @OnClick(R.id.button_next)
    void onClickBtnNext() {
        musicPresenter.onTrackNext();
    }

    @OnClick(R.id.button_list_music)
    void onClickBtnList() {
        Intent intent = new Intent(this, ListMusicActivity.class);
        startActivity(intent);
    }


    public void onLoadListMusic() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String songArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String songImage = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String songLocation = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    Songs song = new Songs(songName, songArtist, songImage, songLocation);
                    songs.add(song);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    @Override
    public void controlPrevious() {
        if (position == 0) {
            Toast.makeText(this, "Đã đến bài đầu tiên của danh sách", Toast.LENGTH_SHORT).show();
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
            }
            position--;
            CreateNotification.createNotification(MusicActivity.this, songs.get(position),
                    R.drawable.ic_pause_blue, position, songs.size() - 1);
            txt_song_title.setText(songs.get(position).getSongName());
            txt_song_author.setText(songs.get(position).getSongArtist());
            musicPresenter.onPlayMusic(songs.get(position).getSongLocation());
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        }
    }

    @Override
    public void controlNext() {
//        if (position == songs.size()) {
//            Toast.makeText(this, "Đã đến bài cuối cùng của danh sách", Toast.LENGTH_SHORT).show();
//        } else {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.reset();
//            }
//            position++;
//            CreateNotification.createNotification(MusicActivity.this, songs.get(position),
//                    R.drawable.ic_pause_blue, position, songs.size() - 1);
            txt_song_title.setText(songs.get(position).getSongName());
            txt_song_author.setText(songs.get(position).getSongArtist());
//            musicPresenter.onPlayMusic(songs.get(position).getSongLocation());
//            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
//        }
        Intent intent = new Intent(MusicService.ACTION_NEXT, null, this, MusicService.class);
        startService(intent);
    }

    @Override
    public void controlPause() {
        CreateNotification.createNotification(MusicActivity.this, songs.get(position),
                R.drawable.ic_play_blue, position, songs.size() - 1);
        img_play.setImageResource(R.drawable.ic_play_blue);
        txt_song_title.setText(songs.get(position).getSongName());
        isPlaying = false;
        mediaPlayer.pause();
    }

    @Override
    public void controlResume() {
        CreateNotification.createNotification(MusicActivity.this, songs.get(position),
                R.drawable.ic_pause_blue, position, songs.size() - 1);
        img_play.setImageResource(R.drawable.ic_pause_blue);
        txt_song_title.setText(songs.get(position).getSongName());
        isPlaying = true;
        musicPresenter.onPlayMusic(songs.get(position).getSongLocation());
        mediaPlayer.start();
    }

    @Override
    public void controlPlayMusic(String url) {
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
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }
            });
            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };
}