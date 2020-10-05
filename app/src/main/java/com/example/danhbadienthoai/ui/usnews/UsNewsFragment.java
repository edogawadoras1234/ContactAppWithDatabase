package com.example.danhbadienthoai.ui.usnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import java.util.List;


public class UsNewsFragment extends Fragment implements UsNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    UsNewsPresenter countriesNewsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);

        recyclerView = view.findViewById(R.id.rv_news);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);

        countriesNewsPresenter = new UsNewsPresenter(this, this);
        countriesNewsPresenter.onLoadJson();
        return view;
    }


    @Override
    public void loadToRecyclerView(List<Article> articles) {
        newsAdapter = new NewsAdapter(articles, getContext());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }
}