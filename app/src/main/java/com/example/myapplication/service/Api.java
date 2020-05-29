package com.example.myapplication.service;

import com.example.myapplication.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("repositories")
    Call<ResponseModel> getDetails(@Query("q") String q, @Query("sort") String sort, @Query("page") int page, @Query("per_page") int per_page);
}
