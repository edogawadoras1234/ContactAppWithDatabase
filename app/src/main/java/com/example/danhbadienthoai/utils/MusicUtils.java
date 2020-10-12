package com.example.danhbadienthoai.utils;

import android.annotation.SuppressLint;

import com.example.danhbadienthoai.data.db.model.Songs;

import java.util.Collections;
import java.util.List;

public class MusicUtils {
    @SuppressLint("DefaultLocale")
    public static String getTimeString(long millis) {

        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        return String.format("%02d", hours) +
                ":" +
                String.format("%02d", minutes) +
                ":" +
                String.format("%02d", seconds);
    }
    public static void sortList(List<Songs> songs){
        Collections.sort(songs, (songs1, t1) -> songs1.getSongName().compareTo(t1.getSongName()));
    }
}
