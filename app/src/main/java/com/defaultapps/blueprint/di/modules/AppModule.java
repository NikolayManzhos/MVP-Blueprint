package com.defaultapps.blueprint.di.modules;

import android.app.Application;
import android.content.Context;

import com.defaultapps.blueprint.data.interactor.MainViewInteractor;
import com.defaultapps.blueprint.data.local.LocalService;
import com.defaultapps.blueprint.data.net.NetworkService;
import com.defaultapps.blueprint.ui.presenter.MainViewPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return  application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application providesApplication() { return application; }

}
