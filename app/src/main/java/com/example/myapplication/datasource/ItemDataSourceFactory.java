package com.example.myapplication.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.myapplication.model.ListModel;

public class ItemDataSourceFactory extends DataSource.Factory {
    //creating the mutable live data
    ItemDataSource itemDataSource;
    private MutableLiveData<PageKeyedDataSource<Integer, ListModel>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, ListModel> create() {
        //getting our data source object
         itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, ListModel>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
    public void invalidate()
    {
        itemDataSource.invalidate();
    }
}
