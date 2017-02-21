package com.defaultapps.blueprint.data.net;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("")
    Call<?> getData();
}
