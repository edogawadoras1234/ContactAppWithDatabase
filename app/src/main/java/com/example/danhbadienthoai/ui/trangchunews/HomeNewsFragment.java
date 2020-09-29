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
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeNewsFragment extends Fragment implements HomeNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeNewsPresenter homeNewsPresenter;
    LinearLayoutManager layoutManager;
    private List<Article> articleArrayList = new ArrayList<>();

    int currentItems, totalItems, scrollOutItems;
    boolean isScrolling = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);

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

        swipeRefreshLayout = view.findViewById(R.id.news_swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    homeNewsPresenter.onScrollData("jack");
                }
            }
        });
        homeNewsPresenter.onLoadData("money");
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