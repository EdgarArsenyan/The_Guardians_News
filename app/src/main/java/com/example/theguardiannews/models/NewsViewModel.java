package com.example.theguardiannews.models;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.theguardiannews.Repos;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private Repos repository;
    private MutableLiveData<List<Result>> result;

    public void getFromRepo(){
        if (result != null){
            return;
        }
        repository = Repos.getInstance();
    }

    public LiveData<List<Result>> getNews(int offset) {
        result = repository.getNewsList(offset);
        return result;
    }
}
