package com.example.danhbadienthoai.ui.japanesenews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JapaneseNewsFragment extends Fragment {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu_news, container, false);
        recyclerView =  view.findViewById(R.id.rv_news);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        loadJSON();

        return view;
    }
    private void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call;
        call = apiInterface.getCountry("jp", API_KEY);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                assert response.body() != null;
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
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}