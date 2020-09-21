package com.example.danhbadienthoai;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.danhbadienthoai.adapter.NewsAdapter;
import com.example.danhbadienthoai.model.Article;
import com.example.danhbadienthoai.model.News;
import com.example.danhbadienthoai.network.ApiClient;
import com.example.danhbadienthoai.network.ApiInterface;
import com.example.danhbadienthoai.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchNewsActivity extends AppCompatActivity {
    EditText edt_search;
    TextView txt_loi;
    Button btn_search, btn_close, btn_tk1, btn_tk2, btn_tk3;
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        findViewByIds();

    }

    private void findViewByIds(){
        recyclerView = (RecyclerView) findViewById(R.id.rv_search_news);
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
        edt_search = findViewById(R.id.edt_search);
        btn_close = findViewById(R.id.btn_activity_close);
        btn_search = findViewById(R.id.btn_activity_search_news);
        txt_loi = findViewById(R.id.txt_loi);
        txt_loi.setVisibility(View.GONE);
        btn_tk1 = findViewById(R.id.tukhoa_1);
        btn_tk2 = findViewById(R.id.tukhoa_2);
        btn_tk3 = findViewById(R.id.tukhoa_3);

        btn_tk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadJSON(btn_tk1.getText().toString().toLowerCase());
            }
        });
        btn_tk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadJSON(btn_tk2.getText().toString().toLowerCase());
            }
        });
        btn_tk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadJSON(btn_tk3.getText().toString().toLowerCase());
            }
        });
        loadJSON("");

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchNewsActivity.this, NewsAppActivity.class);
                startActivity(intent);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_search.length()<1){
                    btn_tk1.setVisibility(View.VISIBLE);
                    btn_tk2.setVisibility(View.VISIBLE);
                    btn_tk3.setVisibility(View.VISIBLE);
                    txt_loi.setVisibility(View.GONE);
                    articles.clear();
                    Toast.makeText(SearchNewsActivity.this, "Nhập từ khoá", Toast.LENGTH_SHORT).show();
                }else {
                    loadJSON(edt_search.getText().toString());
                    Toast.makeText(SearchNewsActivity.this, "Search", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadJSON(String keyword) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String language = Utils.getLanguage();
        Call<News> call;
        call = apiInterface.getQ(keyword,language, "publishedAt", API_KEY);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                        txt_loi.setVisibility(View.VISIBLE);
                    }else {
                        txt_loi.setVisibility(View.GONE);
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
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(SearchNewsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}