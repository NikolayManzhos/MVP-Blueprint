package com.defaultapps.blueprint.di.component;

import com.defaultapps.blueprint.di.modules.AppModule;
import com.defaultapps.blueprint.ui.fragment.MainViewImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainViewImpl mainFragment);
}
