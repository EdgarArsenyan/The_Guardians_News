package com.example.theguardiannews.client;

import com.example.theguardiannews.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheGuardianService {


    String id = "%22it-might-excite-jordan-pickford-but-for-liverpool-it-feels-less-appealing%22";



    @GET("search?show-fields=all&api-key=484ff4f2-7dea-418f-9a3e-ab65dcb0b278")
    Call<User> getResponse(@Query("page") int page);

    @GET("{id}?show-fields=all&api-key=484ff4f2-7dea-418f-9a3e-ab65dcb0b278")
    Call<User> getMarkerItem(@Path("id") String id);
}
