package com.example.myapplication.service;

import android.os.SystemClock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.github.com/search/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {



        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SystemClock.sleep(1000);
                return chain.proceed(chain.request());
            }
        };




        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()

                .addInterceptor(logging)
                .addNetworkInterceptor(interceptor)
                .dispatcher(dispatcher)// <-- this is the important line!
                .build();
        //TODO this remoteService instance will be injected using Dagger in part #2 ...
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
