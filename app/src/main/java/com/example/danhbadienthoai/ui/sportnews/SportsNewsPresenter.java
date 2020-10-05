package com.example.danhbadienthoai.ui.sportnews;

import android.widget.Toast;

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
import retrofit2.HttpException;
import retrofit2.Response;

public class SportsNewsPresenter implements SportsNewsMvpPresenter {

    SportsNewsMvpView sportsNewsMvpView;
    SportsNewsFragment sportsNewsFragment;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";
    int START_PAGE = 1;
    public SportsNewsPresenter(SportsNewsMvpView sportsNewsMvpView, SportsNewsFragment sportsNewsFragment) {
        this.sportsNewsMvpView = sportsNewsMvpView;
        this.sportsNewsFragment = sportsNewsFragment;
    }

    @Override
    public void onLoadData() {
        String language = NewsUtils.getLanguage();
        compositeDisposable.add(apiInterface.getQ2("sport", language, "publishedAt", API_KEY, 10,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<News>() {
                    @Override
                    public void accept(News news) {
                        sportsNewsMvpView.loadToRecyclerView(news.getArticles());
                    }
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        String errorCode;
                        HttpException httpException = (HttpException) throwable;
                        Response<?> response = httpException.response();
                        switch (response.code()) {
                            case 400:
                                errorCode = "Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter";
                                Toast.makeText(sportsNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            case 429:
                                errorCode = "Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while";
                                Toast.makeText(sportsNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                errorCode = "Server Error. Something went wrong on our side";
                                Toast.makeText(sportsNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }));
    }

    @Override
    public void loadToRecyclerView(List<Article> articles) {

    }
}
