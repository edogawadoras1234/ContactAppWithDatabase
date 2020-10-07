package com.example.danhbadienthoai.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.music.MusicActivity;


public class MusicService extends Service {
    private static final String CHANNEL_ID = "Kenh Thong Bao";
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "HelloService";
    MediaPlayer mediaPlayer;
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PREVIOUS = "PREVIOUS";
    public static final String ACTION_NEXT = "NEXT";

    @Override
    public void onCreate() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("This is Music Channel");
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        Log.i(TAG, "Service onCreate");
        mediaPlayer = new MediaPlayer();

        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //Tao event click vao notificate de intent ra main activity
        Intent i = new Intent(this, MusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        Intent intent = new Intent(MusicService.ACTION_NEXT, null, this, MusicService.class);
        PendingIntent pdIntentNext = PendingIntent.getBroadcast(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle("Nhac Dang Mo")//Title cho Notifi
                .setContentText("Nhac dang mo bai nao do")//Content cho Notifi
                .setColor(Color.BLACK)//Mau title
                .setContentIntent(pendingIntent)//Gan event cho notification
                .addAction(R.drawable.quantum_ic_skip_previous_white_24, "Previous", null)//Tạo hành động và icon cho thông báo
                .addAction(R.drawable.ic_baseline_play_circle_outline_24, "Play", null)//
                .addAction(R.drawable.quantum_ic_skip_next_white_24, "Next", pdIntentNext)
                .setPriority(NotificationCompat.PRIORITY_LOW)//Xet do uu tien
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())//hien thi icon cho Notifi
                .setShowWhen(false)//khong cho xo xuong thong bao
                .build();
        //notificationManager.notify(NOTIFICATION_ID, notification); //Thông báo có thể gạt bỏ
        startForeground(NOTIFICATION_ID, notification);//Thông báo không thể gạt bỏ
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_PLAY:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        stopService(new Intent(this, MusicService.class));
                    } else {
                        Intent firstIntent = new Intent(this, MusicService.class);
                        startService(firstIntent);
                        mediaPlayer.start();
                    }
                    break;

                case ACTION_PREVIOUS:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.reset();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);

                        try {
                            mediaPlayer.setDataSource("https://data2.chiasenhac.com/stream2/1727/3/1726451-d9a03b01/128/Mot%20Dieu%20-%20Wowy.mp3");
                            mediaPlayer.prepareAsync();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                        try {
                            mediaPlayer.setDataSource("https://data2.chiasenhac.com/stream2/1727/3/1726451-d9a03b01/128/Mot%20Dieu%20-%20Wowy.mp3");
                            mediaPlayer.prepareAsync();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case ACTION_NEXT:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.reset();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                        try {
                            mediaPlayer.setDataSource("http://www.infinityandroid.com/music/good_times.mp3");
                            mediaPlayer.prepareAsync();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                        try {
                            mediaPlayer.setDataSource("http://www.infinityandroid.com/music/good_times.mp3");
                            mediaPlayer.prepareAsync();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
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

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.i(TAG, "Service onDestroy");
    }

    public void createNotifi() {

        Log.i(TAG, "Service onStartCommand");

    }
}
