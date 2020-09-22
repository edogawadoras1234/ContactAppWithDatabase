package com.example.danhbadienthoai.data.network;

import com.example.danhbadienthoai.data.db.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    @GET("top-headlines")
    Call<News> getSource(
            @Query("sources/id") String sourceid,
            @Query("apiKey") String apiKey
    );
}
