package com.example.danhbadienthoai.ui.usnews;

import com.example.danhbadienthoai.data.db.model.Article;

import java.util.List;

public interface UsNewsMvpPresenter extends UsNewsMvpView {
    void onLoadJson();
}
