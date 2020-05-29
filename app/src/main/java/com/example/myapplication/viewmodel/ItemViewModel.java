package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.myapplication.datasource.ItemDataSource;
import com.example.myapplication.datasource.ItemDataSourceFactory;
import com.example.myapplication.model.ListModel;

public class ItemViewModel extends ViewModel {
    //creating livedata for PagedList  and PagedKeyedDataSource
   public LiveData<PagedList<ListModel>> itemPagedList;
    ItemDataSourceFactory itemDataSourceFactory;
    LiveData<PageKeyedDataSource<Integer, ListModel>> liveDataSource;

    //constructor
    public ItemViewModel() {
        //getting our data source factory
         itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(5)
                        .setPageSize(ItemDataSource.PAGE_SIZE).build();


        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

    public void invalidate()
    {
        itemDataSourceFactory.invalidate();
    }
}
