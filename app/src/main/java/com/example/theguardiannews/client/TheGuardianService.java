package com.example.theguardiannews.client;

import com.example.theguardiannews.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheGuardianService {
    @GET("search?show-fields=all&api-key=484ff4f2-7dea-418f-9a3e-ab65dcb0b278")
    Call<User> getResponse(@Query("page") int page);
}
