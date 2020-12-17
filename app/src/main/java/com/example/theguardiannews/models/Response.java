package com.example.theguardiannews.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {


    @SerializedName("results")
    private List<Result> resultList;
    public List<Result> getResultList() {
        return resultList;
    }
}
