package com.example.danhbadienthoai.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhbadienthoai.network.APIClient;
import com.example.danhbadienthoai.network.ApiInterface;
import com.example.danhbadienthoai.model.Article;
import com.example.danhbadienthoai.model.News;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.utils.Utils;
import com.example.danhbadienthoai.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsNews extends Fragment {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //336c7a92c13b4970be0773e0b2cf5c67 API key
         View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
                recyclerView = (RecyclerView) view.findViewById(R.id.rv_news);
                //Tối ưu hoá dữ liệu trong adapter
                recyclerView.setHasFixedSize(true);

                //Tạo layout
                LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
                //Tạo đường gạch chân giữa các row
                DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(container.getContext(),layoutManager.getOrientation());
                recyclerView.addItemDecoration(deviderItemDecoration);
                loadJSON();

                //336c7a92c13b4970be0773e0b2cf5c67 API key
                return view;
            }
    private void loadJSON() {
        ApiInterface apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        String country = Utils.getCountry();
        String language = Utils.getLanguage();
        Call<News> call;
        call = apiInterface.getQ("sport",language, "publishedAt", API_KEY);
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