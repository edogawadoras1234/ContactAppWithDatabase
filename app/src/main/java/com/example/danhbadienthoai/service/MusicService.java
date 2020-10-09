package com.example.danhbadienthoai.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.music.MusicActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MusicService extends Service {

    private static final String TAG = "HelloService";
    public static final MediaPlayer mediaPlayer = new MediaPlayer();
    private static final String CHANNEL_ID = "Kenh Thong Bao";
    private static final int NOTIFICATION_ID = 1;
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PREVIOUS = "PREVIOUS";
    public static final String ACTION_NEXT = "NEXT";
    public static String TITLE_SONG = "Good Times";
    public static String AUTHOR_SONG = "Instrumental";
    public static String IMAGE_SONG = "https://cf.shopee.vn/file/77ad5ed740fdd4ae05d13a36f840f7b8";
    @Override
    public void onCreate() {
        super.onCreate();
        onPlayMusic("http://www.infinityandroid.com/music/good_times.mp3");
        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        TITLE_SONG = "Good Times";
        AUTHOR_SONG = "Instrumental";
        IMAGE_SONG = "https://cf.shopee.vn/file/77ad5ed740fdd4ae05d13a36f840f7b8";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_PLAY:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        stopForeground(true);
                    } else {
                        mediaPlayer.start();
                        startForeground(NOTIFICATION_ID, getNotification());
                    }
                    break;

                case ACTION_PREVIOUS:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.reset();
                    }
                    TITLE_SONG = "Good Times";
                    AUTHOR_SONG = "Instrumental";
                    IMAGE_SONG = "https://cf.shopee.vn/file/77ad5ed740fdd4ae05d13a36f840f7b8";
                    mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    onPlayMusic("http://www.infinityandroid.com/music/good_times.mp3");
                    break;

                case ACTION_NEXT:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.reset();
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                        onPlayMusic("https://data.chiasenhac.com/down2/2121/5/2120992/128/Thien%20Dang%20-%20Wowy_%20JoliPoli.mp3");
                    }else{
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                        onPlayMusic("https://data.chiasenhac.com/down2/2121/5/2120992/128/Thien%20Dang%20-%20Wowy_%20JoliPoli.mp3");
                    }
                    TITLE_SONG = "Thien Dang";
                    AUTHOR_SONG = "Wowy";
                    IMAGE_SONG = "https://vignette.wikia.nocookie.net/caseclosed/images/8/80/Infobox_-_Conan_Edogawa.png/revision/latest?cb=20190205090116";
                    break;
            }//https://data.chiasenhac.com/down2/2121/5/2120992/128/Thien%20Dang%20-%20Wowy_%20JoliPoli.mp3
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void onPlayMusic(String url) {
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }
    }
    public Notification getNotification() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("This is Music Channel");
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        //Su kien tro lai man hinh phat nhac
        Intent i = new Intent(this, MusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        //Su kien previous button
        Intent intentPrevious = new Intent(this, MusicService.class).setAction(ACTION_PREVIOUS);
        PendingIntent pdIntentPrevious = PendingIntent.getService(this, 0, intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);

        //Su kien previous button
        Intent intentNext = new Intent(this, MusicService.class).setAction(ACTION_NEXT);
        PendingIntent pdIntentNext = PendingIntent.getService(this, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);

        //Su kien play button
        Intent intentPlay = new Intent(this, MusicService.class).setAction(ACTION_PLAY);
        PendingIntent pdIntentPlay = PendingIntent.getService(this, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle(TITLE_SONG)//Title cho Notifi
                .setContentText(AUTHOR_SONG)//Content cho Notifi
                .setColor(Color.BLUE)//Mau title
                .setContentIntent(pendingIntent)//Gan event cho notification
                .addAction(R.drawable.quantum_ic_skip_previous_white_24, "Previous", pdIntentPrevious)//Tạo hành động và icon cho thông báo
                .addAction(R.drawable.ic_baseline_play_circle_outline_24, "Play", pdIntentPlay)//
                .addAction(R.drawable.quantum_ic_skip_next_white_24, "Next", pdIntentNext)
                .setPriority(NotificationCompat.PRIORITY_LOW)//Xet do uu tien
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())//hien thi icon cho Notifi
                .setShowWhen(false)//khong cho xo xuong thong bao
                .build();
    }
}