package com.example.danhbadienthoai.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.danhbadienthoai.CreateNotification;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.Songs;
import com.example.danhbadienthoai.ui.music.MusicActivity;

import java.util.ArrayList;
import java.util.List;


public class MusicService extends Service {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    private static final String CHANNEL_ID = "Kenh Thong Bao";
    private static final int NOTIFICATION_ID = 1;
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PREVIOUS = "PREVIOUS";
    public static final String ACTION_NEXT = "NEXT";
    public static String TITLE_SONG = "Good Times";
    public static String AUTHOR_SONG = "Instrumental";
    public static byte[] IMAGE_SONG;
    public static String LOCATION_SONG = "http://www.infinityandroid.com/music/good_times.mp3";
    private int position = 0;
    List<Songs> songs = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        onLoadListMusic();

        onPlayMusic(LOCATION_SONG);
        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_PLAY:

                    break;

                case ACTION_PREVIOUS:

                    break;

                case ACTION_NEXT:
                    if (position == songs.size()) {
                        Toast.makeText(this, "Đã đến bài cuối cùng của danh sách", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.reset();
                        }
                        position++;
                        CreateNotification.createNotification(this, songs.get(position),
                                R.drawable.ic_pause_blue, position, songs.size() - 1);
                        onPlayMusic(songs.get(position).getSongLocation());
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    }
                    break;
            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

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
}