package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ListModel {
    @SerializedName("owner")
   public SubListModel subListModel;
   public String id;
   public String full_name;
   public String description;
}
