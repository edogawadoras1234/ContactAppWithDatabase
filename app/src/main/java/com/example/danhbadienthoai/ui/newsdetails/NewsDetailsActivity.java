package com.example.danhbadienthoai.ui.newsdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.utils.NewsUtils;

public class NewsDetailsActivity extends AppCompatActivity {
    WebView webview;
    private String mUrl;
    private String mContent;
    TextView txt_title, txt_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news_row_details);

        getIntentInfor();
        setWebview();

    }

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    public void setWebview() {
        webview.setWebViewClient(new WebViewClient());
        webview.loadData(mContent, "text/html", "utf-8");
        webview.loadUrl(mUrl);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDisplayZoomControls(false);//ẩn thu phóng của trang
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.setScrollContainer(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webview.setOnTouchListener((v, event) -> (event.getAction() == MotionEvent.ACTION_MOVE));
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void getIntentInfor() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        String mTitle = intent.getStringExtra("title");
        String mDate = intent.getStringExtra("date");
        mContent = intent.getStringExtra("content");

        webview = findViewById(R.id.webView);
        txt_title = findViewById(R.id.text_title_details);
        txt_time = findViewById(R.id.text_publishAt_details);

        txt_title.setText(mTitle);
        txt_time.setText(NewsUtils.DateFormat(mDate));
    }
}
