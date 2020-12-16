package com.example.theguardiannews.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    public static String BASE_URL = "https://content.guardianapis.com/";

    private static TheGuardianService mApiClient;

    public static TheGuardianService getApiClient(){
        if(mApiClient == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mApiClient = retrofit.create(TheGuardianService.class);
        }
        return mApiClient;
    }
}
