package com.example.danhbadienthoai.ui.listmusic;

import com.example.danhbadienthoai.data.db.model.Songs;

import java.util.List;

public interface ListMusicMvpView {
    void loadMusicList(List<Songs> songs);
}
