package com.example.theguardiannews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {
    @Insert
    void insert(UploadModel dataModel);

    @Delete
    void delete(UploadModel dataModel);

    @Query("SELECT * FROM UploadModel")
    LiveData<List<UploadModel>> getAllData();
}