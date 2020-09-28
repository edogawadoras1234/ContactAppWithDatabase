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

import com.example.danhbadienthoai.EndlessRecyclerViewScrollListener;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;
import com.example.danhbadienthoai.utils.NewsUtils;

import java.util.List;

import butterknife.internal.Utils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeNewsFragment extends Fragment implements HomeNewsMvpView {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeNewsPresenter homeNewsPresenter;
    LinearLayoutManager layoutManager;
    List<Article> articleList2;
    EndlessRecyclerViewScrollListener scrollListener;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";
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

        homeNewsPresenter = new HomeNewsPresenter(this,this);
        homeNewsPresenter.onLoadData("money");

        swipeRefreshLayout = view.findViewById(R.id.news_swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchData();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        return view;
    }

    private void fetchData() {
        Article article = new Article( "Conan",  "title",  "description",  "url",  "urlToImage",  "publishedAt",  "content");
        articleList2.add(article);
        article = new Article( "Conan",  "title",  "description",  "url",  "urlToImage",  "publishedAt",  "content");
        articleList2.add(article);
        article = new Article( "Conan",  "title",  "description",  "url",  "urlToImage",  "publishedAt",  "content");
        articleList2.add(article);
        article = new Article( "Conan",  "title",  "description",  "url",  "urlToImage",  "publishedAt",  "content");
        articleList2.add(article);

        newsAdapter.notifyDataSetChanged();

    }


    @Override
    public void loadData(List<Article> articleList) {
        newsAdapter = new NewsAdapter(articleList, getContext());
        recyclerView.setAdapter(newsAdapter);
        articleList2 = articleList;
    }

    @Override
    public void loadDataScroll(List<Article> articleList) {
        newsAdapter.notifyDataSetChanged();
    }
    private void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = NewsUtils.getCountry();
        String language = NewsUtils.getLanguage();
        Call<News> call;
        call = apiInterface.getQ("covid",language, "publishedAt", API_KEY);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                    articleList2.add((Article) response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}