package com.example.danhbadienthoai.ui.base;

import com.example.danhbadienthoai.data.db.model.Article;

import java.util.List;

public interface MvpView {
    void loadDataByCountry(List<Article> articleList);
    void scrollDataByCountry(List<Article> articleList);
}
