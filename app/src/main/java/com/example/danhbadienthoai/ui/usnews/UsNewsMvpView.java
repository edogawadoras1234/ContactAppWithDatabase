package com.example.danhbadienthoai.ui.usnews;

import com.example.danhbadienthoai.data.db.model.Article;

import java.util.List;

public interface UsNewsMvpView {
    void loadToRecyclerView(List<Article> articles);
}
