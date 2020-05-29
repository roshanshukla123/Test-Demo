package com.example.myapplication.datasource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.myapplication.model.ListModel;
import com.example.myapplication.model.ResponseModel;
import com.example.myapplication.service.RetrofitClient;
import com.example.myapplication.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, ListModel> {

    public static final int PAGE_SIZE = 5;
    private static final int FIRST_PAGE = 1;
    String search;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ListModel> callback) {
        MainActivity.getInstance().show();
        if (!MainActivity.search.equalsIgnoreCase(""))
            search = MainActivity.search;
        else
            search = "android";
        RetrofitClient.getInstance()
                .getApi().getDetails(search, "asc", FIRST_PAGE, 5)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.body() != null) {
                            MainActivity.getInstance().dismiss();

                            callback.onResult(response.body().subListModelList, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                        MainActivity.getInstance().dismiss();
                    }
                });
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ListModel> callback) {
        MainActivity.getInstance().show();
        if (!MainActivity.search.equalsIgnoreCase(""))
            search = MainActivity.search;
        else
            search = "android";
        RetrofitClient.getInstance()
                .getApi().getDetails(search, "asc", params.key, 5)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        MainActivity.getInstance().dismiss();
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            MainActivity.getInstance().dismiss();
                            callback.onResult(response.body().subListModelList, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                        MainActivity.getInstance().dismiss();
                    }
                });
    }


    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ListModel> callback) {
        MainActivity.getInstance().show();
        if (!MainActivity.search.equalsIgnoreCase(""))
            search = MainActivity.search;
        else
            search = "android";
        RetrofitClient.getInstance()
                .getApi().getDetails(search, "asc", params.key, 5)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        MainActivity.getInstance().dismiss();
                        if (response.body() != null) {

                            Integer key = params.key + 1;
                            callback.onResult(response.body().subListModelList, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                        MainActivity.getInstance().dismiss();
                    }
                });
    }
}
