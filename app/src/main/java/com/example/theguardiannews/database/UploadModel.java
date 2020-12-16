package com.example.theguardiannews.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UploadModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String titleModel;
    private String categoryModel;
    private String imageUrlModel;
    private String textModel;


    @NonNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleModel() {
        return titleModel;
    }


    public void setTitleModel(@NonNull String titleModel) {
        this.titleModel = titleModel;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getImageUrlModel() {
        return imageUrlModel;
    }

    public void setImageUrlModel(String imageUrlModel) {
        this.imageUrlModel = imageUrlModel;
    }

    public String getTextModel() {
        return textModel;
    }

    public void setTextModel(String textModel) {
        this.textModel = textModel;
    }
}
