package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    public String total_count;
    public boolean incomplete_results;

    @SerializedName("items")
    public List<ListModel> subListModelList;
}
