package com.example.theguardiannews.models;

import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("bodyText")
    private String bodyText;

    @SerializedName("thumbnail")
    private String thumbnail;

    public Fields(String bodyText, String thumbnail) {
        this.bodyText = bodyText;
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getBodyText() {
        return bodyText;
    }
}
