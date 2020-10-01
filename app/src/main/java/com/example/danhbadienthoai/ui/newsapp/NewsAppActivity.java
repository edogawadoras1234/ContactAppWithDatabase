package com.example.danhbadienthoai.ui.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.searchnew.SearchNewsActivity;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsAppActivity extends AppCompatActivity {
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_news_app);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_search_news) void onClick(){
        Intent intent = new Intent(NewsAppActivity.this, SearchNewsActivity.class);
        startActivity(intent);
    }
}
