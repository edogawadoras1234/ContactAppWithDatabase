package com.example.danhbadienthoai.ui.sportnews;

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

public class SportsNewsPresenter implements SportsNewsMvpPresenter {

    SportsNewsMvpView sportsNewsMvpView;
    SportsNewsFragment sportsNewsFragment;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";

    public SportsNewsPresenter(SportsNewsMvpView sportsNewsMvpView, SportsNewsFragment sportsNewsFragment) {
        this.sportsNewsMvpView = sportsNewsMvpView;
        this.sportsNewsFragment = sportsNewsFragment;
    }

    @Override
    public void onLoadData() {
        String language = NewsUtils.getLanguage();
        compositeDisposable.add(apiInterface.getQ2("sport", language, "publishedAt", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<News>() {
                    @Override
                    public void accept(News news) throws Exception {
                        sportsNewsMvpView.loadToRecyclerView(news.getArticles());
                    }
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        String errorCode;
                        Response response = null;
                        switch (response.code()) {
                            case 404:
                                errorCode = "404 not found";
                                Toast.makeText(sportsNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                errorCode = "500 server broken";
                                Toast.makeText(sportsNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                errorCode = "unknown error";
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
