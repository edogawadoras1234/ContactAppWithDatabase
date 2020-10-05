package com.example.danhbadienthoai.ui.japanesenews;

import com.bumptech.glide.load.HttpException;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.ui.base.MvpPresenter;
import com.example.danhbadienthoai.ui.base.Presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class JapaneseNewsPresenter extends Presenter implements JapaneseNewsMvpPresenter {

    public static final String API_KEY = "9a6572c38e994070ae7c75601e5f9c5a";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    JapaneseNewsMvpView japaneseNewsMvpView;

    public JapaneseNewsPresenter(JapaneseNewsMvpView japaneseNewsMvpView) {
        super();
        this.japaneseNewsMvpView = japaneseNewsMvpView;
    }

    @Override
    public void onLoadDataByCountry(String country, int limit, int page) {
        super.onLoadDataByCountry(country, limit, page);
        compositeDisposable.add(apiInterface.getCountry2(country, API_KEY, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                   // mvpPresenter.loadDataByCountry(news.getArticles());
                }, e -> {
                    if (e instanceof HttpException) {
                        String errorCode;
                        retrofit2.HttpException httpException = (retrofit2.HttpException) e;
                        retrofit2.Response<?> response = httpException.response();
                        switch (response.code()) {
                            case 400:
                                errorCode = "Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter";
                                break;
                            case 429:
                                errorCode = "Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while";
                                break;
                            default:
                                errorCode = "Server Error. Something went wrong on our side";
                                break;
                        }
                    }
                }));
    }

    @Override
    public void onScrollDataByCountry(String country, int limit, int page) {
        super.onScrollDataByCountry(country, limit, page);
    }

    @Override
    public void loadDataByCountry(List<Article> articleList) {
        super.loadDataByCountry(articleList);
    }

    @Override
    public void scrollDataByCountry(List<Article> articleList) {
        super.scrollDataByCountry(articleList);
    }
}
