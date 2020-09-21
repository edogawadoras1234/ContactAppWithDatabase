package com.example.danhbadienthoai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.danhbadienthoai.utils.Utils;

public class NewsDetailsActivity extends AppCompatActivity{
    WebView webview;
    private String mUrl, mTitle, mDate, mAuthor, mContent;
    TextView txt_title, txt_time, txt_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news_row_details);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mTitle = intent.getStringExtra("title");
        mDate = intent.getStringExtra("date");
        mAuthor = intent.getStringExtra("author");
        mContent = intent.getStringExtra("content");

        webview = findViewById(R.id.webView);
        txt_title = findViewById(R.id.txt_title_details);
        txt_time = findViewById(R.id.txt_publishAt_details);

        txt_title.setText(mTitle);
        txt_time.setText(Utils.DateFormat(mDate));
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        Log.i("AAAAAAAAAAAAAAAAANewsDetail","" + mContent);
        //webview.loadDataWithBaseURL(mUrl, mContent, mimeType, encoding, "");

        setWebview();

    }
    @SuppressLint("ClickableViewAccessibility")
    public void setWebview(){

        webview.setWebViewClient(new WebViewClient());
        webview.loadData(mContent,"text/html","utf-8");
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
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);

                webview.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
             return (event.getAction() == MotionEvent.ACTION_MOVE);
           }
          });

    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
