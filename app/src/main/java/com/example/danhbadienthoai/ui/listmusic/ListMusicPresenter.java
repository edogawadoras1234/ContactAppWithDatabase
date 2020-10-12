package com.example.danhbadienthoai.ui.listmusic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.danhbadienthoai.data.db.model.Songs;

import java.util.ArrayList;
import java.util.List;

public class ListMusicPresenter implements ListMusicMvpPresenter {
    ListMusicMvpView listMusicMvpView;
    ListMusicActivity listMusicActivity;
    List<Songs> songs = new ArrayList<>();

    public ListMusicPresenter(ListMusicMvpView listMusicMvpView, ListMusicActivity listMusicActivity){
        this.listMusicMvpView = listMusicMvpView;
        this.listMusicActivity = listMusicActivity;
    }
    @Override
    public void onLoadListMusic() {
        ContentResolver contentResolver = listMusicActivity.getContentResolver();
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
            listMusicMvpView.loadMusicList(songs);
        }
    }

    @Override
    public void loadMusicList(List<Songs> songs) {

    }
}
