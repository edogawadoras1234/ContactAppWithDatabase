package com.example.danhbadienthoai;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private List<Article> articleList;
    Context context;
    public NewsAdapter(List<Article> articleList, Context context) {
        this.context = context;
        this.articleList = articleList;
    }


    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        NewsAdapterViewHolder viewHolder = new NewsAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapterViewHolder holder, final int position) {
        holder.txt_title.setText(articleList.get(position).getTitle());
        holder.txt_author.setText(articleList.get(position).getAuthor());
        //holder.txt_desc.setText(articleList.get(position).getDescription());
        holder.txt_publishat.setText(Utils.DateFormat(articleList.get(position).getPublishedAt()));
        String hinhanh = articleList.get(position).getUrlToImage();

        Glide.with(holder.itemView.getContext()).load(hinhanh)
                .placeholder(R.drawable.icon_news).into(holder.img_news);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),NewsDetails.class);
                intent.putExtra("url", articleList.get(position).getUrl());
                intent.putExtra("title", articleList.get(position).getTitle());
                intent.putExtra("img",articleList.get(position).getUrlToImage());
                intent.putExtra("date", articleList.get(position).getPublishedAt());
                intent.putExtra("author",  articleList.get(position).getAuthor());
                intent.putExtra("content", articleList.get(position).getContent());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder{
    TextView txt_author, txt_title, txt_publishat;
    ImageView img_news;
        public NewsAdapterViewHolder(@NonNull final View itemview){
            super(itemview);
            txt_author = itemview.findViewById(R.id.txt_author_news);
            txt_title = itemview.findViewById(R.id.txt_title_news);
            txt_publishat = itemview.findViewById(R.id.txt_publishAt);
            img_news = itemview.findViewById(R.id.img_news);

        }
    }
}
