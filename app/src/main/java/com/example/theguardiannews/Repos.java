package com.example.theguardiannews;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.theguardiannews.client.ApiManager;
import com.example.theguardiannews.client.TheGuardianService;
import com.example.theguardiannews.database.DataDao;
import com.example.theguardiannews.database.DatabaseHelper;
import com.example.theguardiannews.database.UploadModel;
import com.example.theguardiannews.models.Result;
import com.example.theguardiannews.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repos {
    private static Repos repository;
    private TheGuardianService service;
    private DataDao dataDao;
    private LiveData<List<UploadModel>> uploadModel;

    public Repos() {
        service = ApiManager.getApiClient();
    }

    public Repos(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        dataDao = db.getDataDao();
        uploadModel = dataDao.getAllData();
    }

    public static Repos getInstance(){
        if (repository ==null){
            repository = new Repos();
        }
        return repository;
    }

    public void insert(UploadModel uModel){
        Log.e("sdfsdf ", "repos>insert()");

        new insertAsyncTask(dataDao).execute(uModel);
    }

    public void delete(UploadModel uModel){
        Log.e("sdfsdf ", "repos>insert()");

        new DeleteAsyncTask(dataDao).execute(uModel);
    }

    public LiveData<List<UploadModel>> getFromDB() {
        return uploadModel;
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

    private static class DeleteAsyncTask extends AsyncTask<UploadModel, Void, Void> {

        private DataDao mAsyncTaskDao;

        DeleteAsyncTask(DataDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground( UploadModel... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<UploadModel, Void, Void> {

        private DataDao mAsyncTaskDao;

        insertAsyncTask(DataDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( UploadModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
