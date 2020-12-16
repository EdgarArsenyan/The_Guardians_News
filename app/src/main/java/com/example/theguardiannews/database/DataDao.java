package com.example.theguardiannews.database;

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
    List<UploadModel> getAllData();

//    @Query("SELECT * FROM DataModel WHERE title LIKE :title")
//    List<DataModel> getAllInfo();
}