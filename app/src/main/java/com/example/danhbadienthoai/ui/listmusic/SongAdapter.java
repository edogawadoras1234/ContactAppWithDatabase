package com.example.danhbadienthoai.ui.listmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.Songs;
import com.example.danhbadienthoai.ui.music.MusicActivity;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Songs> songsList;

    public SongAdapter(Context context, List<Songs> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music_row, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SongAdapter.SongViewHolder) {
            final SongAdapter.SongViewHolder songViewHolder = (SongAdapter.SongViewHolder) holder;

            String name = songsList.get(position).getSongName();
            String author = songsList.get(position).getSongArtist();
            String location = songsList.get(position).getSongLocation();
            songViewHolder.txt_title_song.setText(name);
            songViewHolder.txt_author_song.setText(author);

            byte[] image = getAlbumArt(songsList.get(position).getSongLocation());
            Glide.with(context)
                    .asBitmap()
                    .load(image)
                    .placeholder(R.drawable.ic_music_background).into(songViewHolder.img_song);

            songViewHolder.itemView.setOnClickListener(view -> {
                Log.d("MainActivity", "Clicked");
                Intent intent = new Intent(context, MusicActivity.class);
                intent.putExtra("songName", name);
                intent.putExtra("songLocation", location);
                intent.putExtra("songAuthor", author);
                intent.putExtra("songImage",image);
                context.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        return songsList.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title_song, txt_author_song;
        ImageView img_song;

        @SuppressLint("CutPasteId")
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_author_song = itemView.findViewById(R.id.text_author_music_item);
            txt_title_song = itemView.findViewById(R.id.text_title_music_item);
            img_song = itemView.findViewById(R.id.image_item_music);
        }
    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
