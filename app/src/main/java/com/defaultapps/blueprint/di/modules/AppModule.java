package com.defaultapps.blueprint.di.modules;

import android.app.Application;

import com.defaultapps.blueprint.data.interactor.MainViewInteractor;
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
    MainViewPresenterImpl getPresenter(MainViewInteractor mainViewInteractor){
        return new MainViewPresenterImpl(mainViewInteractor);
    }

    @Provides
    MainViewInteractor getMainViewInteractor() {
        return new MainViewInteractor();
    }
}
