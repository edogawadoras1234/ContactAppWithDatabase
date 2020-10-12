package com.example.danhbadienthoai.ui.listmusic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.Songs;
import com.example.danhbadienthoai.utils.MusicUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class ListMusicActivity extends AppCompatActivity implements ListMusicMvpView {
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    ListMusicPresenter listMusicPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);

        recyclerView = findViewById(R.id.rv_list_music);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        listMusicPresenter = new ListMusicPresenter(this, this);

        Dexter.withActivity(this)
                .withPermission(READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(READ_EXTERNAL_STORAGE)) {
                            listMusicPresenter.onLoadListMusic();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ListMusicActivity.this, "Can truy cap vao bo nho", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    @Override
    public void loadMusicList(List<Songs> songs) {
        songAdapter = new SongAdapter(this, songs);
        recyclerView.setAdapter(songAdapter);
        MusicUtils.sortList(songs);
        songAdapter.notifyDataSetChanged();
        MusicUtils.sortList(songs);
    }
}