package com.example.danhbadienthoai.ui.usnews;


import android.widget.Toast;


import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UsNewsPresenter implements UsNewsMvpPresenter {
    UsNewsMvpView usNewsMvpView;
    UsNewsFragment usNewsFragment;
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiInterface apiInterface;

    public UsNewsPresenter(UsNewsMvpView usNewsMvpView, UsNewsFragment usNewsFragment) {
        this.usNewsMvpView = usNewsMvpView;
        this.usNewsFragment = usNewsFragment;

    }

    @Override
    public void onLoadJson() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        compositeDisposable.add(apiInterface.getCountry2("us", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> usNewsMvpView.loadToRecyclerView(news.getArticles())));
    }

    @Override
    public void loadToRecyclerView(List<Article> articles) {

    }
}
