package com.example.theguardiannews;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.theguardiannews.client.ApiManager;
import com.example.theguardiannews.client.TheGuardianService;
import com.example.theguardiannews.models.Result;
import com.example.theguardiannews.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repos {
    private static Repos repository;
    private TheGuardianService service;

    public Repos() {
        service = ApiManager.getApiClient();
    }

    public static Repos getInstance(){
        if (repository ==null){
            repository = new Repos();
        }
        return repository;
    }

    public MutableLiveData<List<Result>> getNewsList(int offset) {
        final MutableLiveData<List<Result>> results = new MutableLiveData<>();

        Call<User> call = ApiManager.getApiClient().getResponse(offset);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.isSuccessful() && response.body().getResponse().getResultList() != null){
                    results.setValue(response.body().getResponse().getResultList());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("OnFailoure ", t.getMessage());
            }
        });
        return results;
    }
}
