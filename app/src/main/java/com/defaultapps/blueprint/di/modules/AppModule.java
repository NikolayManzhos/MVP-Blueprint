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
    Application providesApplication() {
        return  application;
    }

    @Provides
    @Singleton
    MainViewPresenterImpl getMainViewPresenter(MainViewInteractor mainViewInteractor){
        return new MainViewPresenterImpl(mainViewInteractor);
    }

    @Provides
    MainViewInteractor getMainViewInteractor(NetworkService networkService, LocalService localService) {
        return new MainViewInteractor(networkService, localService);
    }

    @Provides
    NetworkService getNetworkService() { return new NetworkService(); }

    @Provides
    LocalService getLocalService() { return new LocalService(application); }

    @Provides
    Context provideContext() { return application.getApplicationContext(); }
}
