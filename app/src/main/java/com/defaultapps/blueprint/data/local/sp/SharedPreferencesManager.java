package com.defaultapps.blueprint.data.local.sp;


import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class SharedPreferencesManager {

    private SharedPreferencesHelper sharedPreferencesHelper;

    private final String CACHE_TIME = "cache_exp_time";


    @Inject
    public SharedPreferencesManager(SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public void setCacheTime(long currentTime) {
        sharedPreferencesHelper.putLong(CACHE_TIME, currentTime);
    }

    public long getCacheTime() {
        return sharedPreferencesHelper.getLong(CACHE_TIME);
    }

}
