package com.example.danhbadienthoai.ui.trangchunews;

import com.example.danhbadienthoai.data.db.model.Article;

import java.util.List;

public interface HomeNewsMvpView {
    void loadData(List<Article> articleList);
    void loadDataScroll(List<Article> articleList);
}
