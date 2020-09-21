package com.example.danhbadienthoai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.danhbadienthoai.adapter.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NewsAppActivity extends AppCompatActivity {
    public TabLayout tabLayout;
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_news_app);

//        getSupportActionBar().setTitle("Báo Mới");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        btn_search = findViewById(R.id.btn_search_news);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsAppActivity.this, SearchNewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
