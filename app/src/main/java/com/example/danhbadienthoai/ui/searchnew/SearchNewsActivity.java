package com.example.danhbadienthoai.ui.searchnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danhbadienthoai.ui.newsapp.NewsAppActivity;
import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.newsapp.NewsAdapter;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.utils.NewsUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchNewsActivity extends AppCompatActivity {

    @BindView(R.id.edit_search)
    EditText edt_search;
    @BindView(R.id.button_keyword_1)
    Button btn_tk1;
    @BindView(R.id.button_keyword_2)
    Button btn_tk2;
    @BindView(R.id.button_keyword_3)
    Button btn_tk3;
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        findViewByIds();
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_keyword_1)
    void onClickKeyWord1() {
        loadJSON(btn_tk1.getText().toString().toLowerCase());
    }

    @OnClick(R.id.button_keyword_2)
    void onClickKeyWord2() {
        loadJSON(btn_tk2.getText().toString().toLowerCase());
    }

    @OnClick(R.id.button_keyword_3)
    void onClickKeyWord3() {
        loadJSON(btn_tk3.getText().toString().toLowerCase());
    }

    @OnClick(R.id.button_news_close)
    void onClickClose() {
        Intent intent = new Intent(SearchNewsActivity.this, NewsAppActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_search_news)
    void onClickSearch() {
        if (edt_search.length() < 1) {
            btn_tk1.setVisibility(View.VISIBLE);
            btn_tk2.setVisibility(View.VISIBLE);
            btn_tk3.setVisibility(View.VISIBLE);
            articles.clear();
            Toast.makeText(SearchNewsActivity.this, "Nhập từ khoá", Toast.LENGTH_SHORT).show();
        } else {
            loadJSON(edt_search.getText().toString());
            Toast.makeText(SearchNewsActivity.this, "Search", Toast.LENGTH_SHORT).show();
        }
    }

    private void findViewByIds() {
        recyclerView = findViewById(R.id.rv_search_news);
        //Tối ưu hoá dữ liệu trong adapter
        recyclerView.setHasFixedSize(true);

        //Tạo layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        loadJSON("");
    }

    private void loadJSON(String keyword) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String language = NewsUtils.getLanguage();
        Call<News> call;
        call = apiInterface.getQ(keyword, language, "publishedAt", API_KEY);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    } else {
                        btn_tk1.setVisibility(View.GONE);
                        btn_tk2.setVisibility(View.GONE);
                        btn_tk3.setVisibility(View.GONE);
                        articles = response.body().getArticles();
                        newsAdapter = new NewsAdapter(articles, SearchNewsActivity.this);
                        recyclerView.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
                Toast.makeText(SearchNewsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}