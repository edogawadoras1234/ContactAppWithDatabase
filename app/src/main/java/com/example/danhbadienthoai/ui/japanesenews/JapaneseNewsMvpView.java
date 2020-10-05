package com.example.danhbadienthoai.ui.japanesenews;

import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.ui.base.MvpView;

import java.util.List;

public interface JapaneseNewsMvpView extends MvpView {
    @Override
    void loadDataByCountry(List<Article> articleList);

    @Override
    void scrollDataByCountry(List<Article> articleList);
}
