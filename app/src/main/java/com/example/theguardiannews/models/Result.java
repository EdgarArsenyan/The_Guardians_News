package com.example.theguardiannews.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Result {

    @SerializedName("sectionName")
    private String mCategory;

    @SerializedName("webPublicationDate")
    private Date date;

    @SerializedName("webTitle")
    private String mTitle;

    @SerializedName("fields")
    private Fields mFields;

    @SerializedName("id")
    private String id;

    public Result(String mCategory, Date date, String mTitle, Fields mFields, String id) {
        this.mCategory = mCategory;
        this.date = date;
        this.mTitle = mTitle;
        this.mFields = mFields;
        this.id = id;
    }

    public Fields getmFields() {
        return mFields;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmCategory() {
        return mCategory;
    }

}
