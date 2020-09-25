package com.example.danhbadienthoai.ui.trangchunews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import java.util.List;


public class HomeNewsFragment extends Fragment implements HomeNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeNewsPresenter homeNewsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);

        findViewByIds(view, container);
        homeNewsPresenter = new HomeNewsPresenter(this);
        homeNewsPresenter.onLoadData();

        swipeRefreshLayout = view.findViewById(R.id.news_swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();

            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    public void findViewByIds(View view, ViewGroup container) {
        recyclerView = view.findViewById(R.id.rv_news);
        //Tối ưu hoá dữ liệu trong adapter
        recyclerView.setHasFixedSize(true);

        //Tạo layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public void loadData(List<Article> articleList) {
        newsAdapter = new NewsAdapter(articleList, getContext());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }
}