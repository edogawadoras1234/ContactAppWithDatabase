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

import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.utils.NewsUtils;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import java.util.List;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangChuNewsFragment extends Fragment {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_news);
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
       loadJSON();

       swipeRefreshLayout = view.findViewById(R.id.news_swipe);
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();

               swipeRefreshLayout.setRefreshing(false);
           }
       });
        //336c7a92c13b4970be0773e0b2cf5c67 API key
        return view;
    }

    private void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = NewsUtils.getCountry();
        String language = NewsUtils.getLanguage();
        Call<News> call;
        call = apiInterface.getQ("money",language, "publishedAt", API_KEY);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    newsAdapter = new NewsAdapter(articles, getContext());
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}