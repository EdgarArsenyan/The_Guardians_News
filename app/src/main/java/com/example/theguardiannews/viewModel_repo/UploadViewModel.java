package com.example.theguardiannews.viewModel_repo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.theguardiannews.viewModel_repo.Repos;
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
        repos.delete(uploadModel);
    }

    public void insert(UploadModel uploadModel){
        repos.insert(uploadModel);
    }
}
