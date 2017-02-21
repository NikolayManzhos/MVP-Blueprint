package com.defaultapps.blueprint.data.net;


import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private Retrofit nonCachedRetrofit;

    @Inject
    public NetworkService() {
        nonCachedRetrofit = getNonCahcedRetrofitCall();
    }

    private Retrofit getNonCahcedRetrofitCall() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Api getNetworkCall() {
        return nonCachedRetrofit.create(Api.class);
    }
}
