package com.defaultapps.blueprint.data.net;

import com.defaultapps.blueprint.data.entity.PhotoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("photos?albumId=1")
    Call<List<PhotoResponse>> getData();
}
