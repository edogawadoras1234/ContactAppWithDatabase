package com.example.danhbadienthoai.data.network;

import com.example.danhbadienthoai.data.db.model.Article;
import com.example.danhbadienthoai.data.db.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Observable;

public interface ApiInterface {
    @GET("everything")
    Call<News> getQ(
            @Query("q") String q,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
    @GET("top-headlines")
    Call<News> getCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    //Get bang RxAndroid
    @GET("top-headlines")
    Observable<News> getCountry2(
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize,
            @Query("page") int pageIndex
    );
    @GET("everything")
    Observable<News> getQ2(
            @Query("q") String q,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize,
            @Query("page") int pageIndex
    );
}
