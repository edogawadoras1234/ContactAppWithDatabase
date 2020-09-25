package com.example.danhbadienthoai.ui.sportnews;

import com.example.danhbadienthoai.data.db.model.Article;

import java.util.List;

public interface SportsNewsMvpView {
    void loadToRecyclerView(List<Article> articles);
}
