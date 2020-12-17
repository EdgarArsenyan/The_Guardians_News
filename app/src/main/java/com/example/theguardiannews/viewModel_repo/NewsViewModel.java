package com.example.theguardiannews.viewModel_repo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.theguardiannews.models.Result;
import com.example.theguardiannews.viewModel_repo.Repos;

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
