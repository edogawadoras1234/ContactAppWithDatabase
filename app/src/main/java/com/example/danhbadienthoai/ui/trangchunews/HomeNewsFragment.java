package com.example.danhbadienthoai.ui.trangchunews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.RecyclerViewScrollListener;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeNewsFragment extends Fragment implements HomeNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    HomeNewsPresenter homeNewsPresenter;
    LinearLayoutManager layoutManager;
    private List<Article> articleArrayList = new ArrayList<>();
    ProgressBar progressBar;
    int OFFSET = 1;
    int LIMIT = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rv_news);
        //Tối ưu hoá dữ liệu trong adapter
        recyclerView.setHasFixedSize(true);
        //Tạo layout
        layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        homeNewsPresenter = new HomeNewsPresenter(this, this);

        RecyclerViewScrollListener scrollListener = new RecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                homeNewsPresenter.onScrollData("jack", LIMIT, page);
//                if (totalItemsCount == ) {
//                    Toast.makeText(getContext(), "No More To Load", Toast.LENGTH_SHORT).show();
//                }
                Log.i("Offset khi scroll", "offset = " + (articleArrayList.size() + 1));
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        homeNewsPresenter.onLoadData("jack", LIMIT, OFFSET);
        Log.i("OFFSET khi chua scroll", " " + OFFSET);
        return view;
    }

    @Override
    public void loadData(List<Article> articleList) {
        newsAdapter = new NewsAdapter(articleList, getContext());
        articleArrayList = articleList;
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void loadDataScroll(List<Article> articleList) {
        articleArrayList.addAll(articleList);
        newsAdapter.notifyDataSetChanged();
    }
}