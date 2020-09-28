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
import okhttp3.Response;

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
                .subscribe(news -> usNewsMvpView.loadToRecyclerView(news.getArticles()), throwable -> {
                    if (throwable instanceof HttpException) {
                        String errorCode;
                        Response response = null;
                        switch (response.code()) {
                            case 404:
                                errorCode = "404 not found";
                                Toast.makeText(usNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                errorCode = "500 server broken";
                                Toast.makeText(usNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                errorCode = "unknown error";
                                Toast.makeText(usNewsFragment.getContext(), " " + errorCode, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }));
    }

    @Override
    public void loadToRecyclerView(List<Article> articles) {

    }
}
