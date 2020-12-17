package com.example.theguardiannews.models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.theguardiannews.Repos;
import com.example.theguardiannews.database.UploadModel;

import java.util.List;

public class UploadViewModel extends AndroidViewModel {

    private Repos repos;
    private LiveData<List<UploadModel>> model;

    public UploadViewModel(@NonNull Application application) {
        super(application);
        repos = new Repos(application);
        model = repos.getFromDB();
    }

    public LiveData<List<UploadModel>> getUploadModel(){
        return model;
    }

    public void delete(UploadModel uploadModel){
        Log.e("sdfsdf ", "viewModel.insert()");
        repos.delete(uploadModel);
    }

    public void insert(UploadModel uploadModel){
        Log.e("sdfsdf ", "viewModel.insert()");
        repos.insert(uploadModel);
    }
}
