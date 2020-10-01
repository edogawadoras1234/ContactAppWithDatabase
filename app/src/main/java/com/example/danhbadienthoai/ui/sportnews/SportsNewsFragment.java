package com.example.danhbadienthoai.ui.sportnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import java.util.List;


public class SportsNewsFragment extends Fragment implements SportsNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
   SportsNewsPresenter sportsNewsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);
        recyclerView =  view.findViewById(R.id.rv_news);
        //Tối ưu hoá dữ liệu trong adapter
        recyclerView.setHasFixedSize(true);

        //Tạo layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
       sportsNewsPresenter = new SportsNewsPresenter(this, this);
       sportsNewsPresenter.onLoadData();

        return view;
    }


    @Override
    public void loadToRecyclerView(List<Article> articles) {
        newsAdapter = new NewsAdapter(articles, getContext());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }
}