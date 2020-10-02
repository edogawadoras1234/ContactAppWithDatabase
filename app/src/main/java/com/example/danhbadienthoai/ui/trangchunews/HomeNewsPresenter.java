package com.example.danhbadienthoai.ui.trangchunews;

import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.load.HttpException;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;
import com.example.danhbadienthoai.utils.NewsUtils;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class HomeNewsPresenter implements HomeNewsMvpPresenter {
    HomeNewsMvpView homeNewsMvpView;
    HomeNewsFragment homeNewsFragment;
    public static final String API_KEY = "9a6572c38e994070ae7c75601e5f9c5a";
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String language = NewsUtils.getLanguage();


    public HomeNewsPresenter(HomeNewsMvpView homeNewsMvpView, HomeNewsFragment homeNewsFragment) {
        this.homeNewsMvpView = homeNewsMvpView;
        this.homeNewsFragment = homeNewsFragment;
    }

    @Override
    public void onLoadData(String q, int limit, int offset) {
        compositeDisposable.add(apiInterface.getQ2(q, language, "publishedAt", API_KEY, limit,offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<News>() {
                    @Override
                    public void accept(News news) throws HttpException {
                        Log.i("Total Resuilt", " " + news.getTotalResults());
                        Toast.makeText(homeNewsFragment.getContext(), "Total Resuilt: " + news.getTotalResults(), Toast.LENGTH_SHORT).show();
                        homeNewsMvpView.loadData(news.getArticles());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) {
                        if (e instanceof HttpException) {

                        }
                    }
                }));

    }

    @Override
    public void onScrollData(String q, int limit, int offset) {
        compositeDisposable.add(apiInterface.getQ2(q, language, null, API_KEY, limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                    List<Article> articles = news.getArticles();
                    homeNewsMvpView.loadDataScroll(articles);
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        String errorCode;
                        Response response = null;
                        switch (response.code()) {
                            case 404:
                                errorCode = "404 not found";
                                Toast.makeText(homeNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                errorCode = "500 server broken";
                                Toast.makeText(homeNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                errorCode = "unknown error";
                                Toast.makeText(homeNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }));
    }


    @Override
    public void loadData(List<Article> articleList) {

    }

    @Override
    public void loadDataScroll(List<Article> articleList) {

    }
}
