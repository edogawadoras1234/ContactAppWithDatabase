package com.example.danhbadienthoai.ui.trangchunews;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.bumptech.glide.load.HttpException;
import com.example.danhbadienthoai.EndlessRecyclerViewScrollListener;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;
import com.example.danhbadienthoai.utils.NewsUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.internal.Utils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class HomeNewsFragment extends Fragment implements HomeNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeNewsPresenter homeNewsPresenter;
    LinearLayoutManager layoutManager;
    private List<Article> articleArrayList = new ArrayList<>();
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";


    int currentItems, totalItems, scrollOutItems;
    boolean isScrolling = false;
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    String language = NewsUtils.getLanguage();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);

        recyclerView = view.findViewById(R.id.rv_news);
        //Tối ưu hoá dữ liệu trong adapter
        recyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(articleArrayList, getContext());
        recyclerView.setAdapter(newsAdapter);

        //Tạo layout
        layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        homeNewsPresenter = new HomeNewsPresenter(this,this);


        swipeRefreshLayout = view.findViewById(R.id.news_swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;
                    loadJSON2demo("jack");
                    //fetchData();
                }
            }
        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//                {
//                    isScrolling = true;
//                }
//            }
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                currentItems = layoutManager.getChildCount();
//                totalItems = layoutManager.getItemCount();
//                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
//
//                if(isScrolling && (currentItems + scrollOutItems == totalItems))
//                {
//                    isScrolling = false;
//                    //fetchData();
//                    loadJSON2demo("jack");
//                }
//            }
//        });
        loadJSON("money");

        return view;
    }



    @Override
    public void loadData(List<Article> articleList) {
        newsAdapter = new NewsAdapter(articleList, getContext());
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void loadDataScroll(List<Article> articleList) {
        newsAdapter.notifyDataSetChanged();
    }
    private void loadJSON(String q) {
        Call<News> call;
        call = apiInterface.getQ(q, language, null, API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, retrofit2.Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articleArrayList.isEmpty()) {
                        articleArrayList.clear();
                    }
                    articleArrayList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadJSON2demo(String q) {
        Call<News> call;
        call = apiInterface.getQ(q, language, null, API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, retrofit2.Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){

                    List<Article> articles = response.body().getArticles();
                    articleArrayList.addAll(articles);
                    newsAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}