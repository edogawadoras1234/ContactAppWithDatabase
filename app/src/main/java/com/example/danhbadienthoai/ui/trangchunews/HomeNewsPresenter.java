package com.example.danhbadienthoai.ui.trangchunews;

import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.utils.NewsUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeNewsPresenter implements  HomeNewsMvpPresenter {
    HomeNewsMvpView homeNewsMvpView;
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomeNewsPresenter(HomeNewsMvpView homeNewsMvpView){
        this.homeNewsMvpView = homeNewsMvpView;
    }
    @Override
    public void onLoadData() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String language = NewsUtils.getLanguage();
        compositeDisposable.add(apiInterface.getQ2("money", language, "publishedAt", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> homeNewsMvpView.loadData(news.getArticles())));
    }


    @Override
    public void loadData(List<Article> articleList) {

    }
}
