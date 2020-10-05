package com.example.danhbadienthoai.ui.base;

import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.load.HttpException;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class Presenter implements MvpPresenter {

    MvpPresenter mvpPresenter;


    @Override
    public void onLoadDataByCountry(String country,  int limit, int page) {

    }

    @Override
    public void onScrollDataByCountry(String country, int limit, int page) {

    }

    @Override
    public void loadDataByCountry(List<Article> articleList) {

    }

    @Override
    public void scrollDataByCountry(List<Article> articleList) {

    }
}
