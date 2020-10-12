package com.example.danhbadienthoai.data.db.model;

import org.jetbrains.annotations.NotNull;

public class Songs {
    String songName, songArtist, songImage, songLocation;

    public Songs(String songName, String songArtist, String songImage, String songLocation) {
        this.songName = songName;
        this.songArtist = songArtist;
        this.songImage = songImage;
        this.songLocation = songLocation;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongLocation() {
        return songLocation;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSongLocation(String songLocation) {
        this.songLocation = songLocation;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", songLocation='" + songLocation + '\'' +
                '}';
    }
}
