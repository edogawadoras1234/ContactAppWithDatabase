package com.example.danhbadienthoai.ui.usnews;


import android.widget.Toast;


import com.bumptech.glide.load.HttpException;
import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.db.model.News;
import com.example.danhbadienthoai.data.network.ApiClient;
import com.example.danhbadienthoai.data.network.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

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
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        compositeDisposable.add(apiInterface.getCountry2("us", API_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(news -> usNewsMvpView.loadToRecyclerView(news.getArticles()), throwable -> {
//                    if (throwable instanceof HttpException) {
//                        String errorCode;
//                        retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
//                        Response<?> response = httpException.response();
//                        switch (response.code()) {
//                            case 400:
//                                errorCode = "Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter";
//                                Toast.makeText(usNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
//                                break;
//                            case 429:
//                                errorCode = "Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while";
//                                Toast.makeText(usNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
//                                break;
//                            default:
//                                errorCode = "Server Error. Something went wrong on our side";
//                                Toast.makeText(usNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                    }
//                }));
    }

    @Override
    public void loadToRecyclerView(List<Article> articles) {

    }
}
