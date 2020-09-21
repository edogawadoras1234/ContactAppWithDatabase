package com.example.danhbadienthoai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.danhbadienthoai.model.Article;
import com.example.danhbadienthoai.NewsDetailsActivity;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.utils.Utils;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> articleList;
    Context context;
    public static final int VIEW_TYPE_1 = 0;
    public static final int VIEW_TYPE_2 = 1;
    public NewsAdapter(List<Article> articleList, Context context) {
        this.context = context;
        this.articleList = articleList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_row, parent, false);
            NewsAdapterViewHolder viewHolder = new NewsAdapterViewHolder(view);

            return viewHolder;
        }
        else if(viewType == VIEW_TYPE_2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_row_type_2, parent, false);
            NewsAdapterViewHolderType2 viewHolderType2 = new NewsAdapterViewHolderType2(view);

            return viewHolderType2;
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_row, parent, false);
            NewsAdapterViewHolder viewHolder = new NewsAdapterViewHolder(view);

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof NewsAdapterViewHolder) {
            NewsAdapterViewHolder newsAdapterViewHolder = (NewsAdapterViewHolder) holder;
            newsAdapterViewHolder.txt_title.setText(articleList.get(position).getTitle());
            newsAdapterViewHolder.txt_author.setText(articleList.get(position).getAuthor());
            newsAdapterViewHolder.txt_publishat.setText(Utils.DateFormat(articleList.get(position).getPublishedAt()));
            String hinhanh = articleList.get(position).getUrlToImage();

            Glide.with(holder.itemView.getContext()).load(hinhanh)
                    .placeholder(R.drawable.ic_newsss).into(newsAdapterViewHolder.img_news);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), NewsDetailsActivity.class);
                    intent.putExtra("url", articleList.get(position).getUrl());
                    intent.putExtra("title", articleList.get(position).getTitle());
                    intent.putExtra("img", articleList.get(position).getUrlToImage());
                    intent.putExtra("date", articleList.get(position).getPublishedAt());
                    intent.putExtra("author", articleList.get(position).getAuthor());
                    intent.putExtra("content", articleList.get(position).getContent());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }else if (holder instanceof NewsAdapterViewHolderType2){
            NewsAdapterViewHolderType2 newsAdapterViewHolderType2 = (NewsAdapterViewHolderType2) holder;
            newsAdapterViewHolderType2.txt_title_type2.setText(articleList.get(position).getTitle());
            newsAdapterViewHolderType2.txt_author_type2.setText(articleList.get(position).getAuthor());
            String hinhanh = articleList.get(position).getUrlToImage();

            Glide.with(holder.itemView.getContext())
                    .load(hinhanh)
                    .placeholder(R.drawable.ic_newsss)
                    .into(newsAdapterViewHolderType2.img_news_type2);

            Animation rotate = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
            newsAdapterViewHolderType2.img_news_type2.startAnimation(rotate);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), NewsDetailsActivity.class);
                    intent.putExtra("url", articleList.get(position).getUrl());
                    intent.putExtra("title", articleList.get(position).getTitle());
                    intent.putExtra("img", articleList.get(position).getUrlToImage());
                    intent.putExtra("date", articleList.get(position).getPublishedAt());
                    intent.putExtra("author", articleList.get(position).getAuthor());
                    intent.putExtra("content", articleList.get(position).getContent());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(articleList.get(position).getTitle().toLowerCase().contains("covid") || articleList.get(position).getTitle().toLowerCase().contains("usd")){
            return VIEW_TYPE_2;
        }
        return VIEW_TYPE_1;
//        for (int i = position; i <= articleList.size(); i++) {
//            if (i %2==0) {
//                return VIEW_TYPE_2;
//            }
//            return VIEW_TYPE_1;
//        }
//        return VIEW_TYPE_1;
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
    public class NewsAdapterViewHolderType2 extends RecyclerView.ViewHolder{
        TextView txt_author_type2, txt_title_type2;
        ImageView img_news_type2;
        public NewsAdapterViewHolderType2(@NonNull final View itemview){
            super(itemview);
            txt_author_type2 = itemview.findViewById(R.id.txt_author_news_type_2);
            txt_title_type2 = itemview.findViewById(R.id.txt_title_news_type_2);
            img_news_type2 = itemview.findViewById(R.id.img_news_type_2);

        }
    }

}
