package com.defaultapps.blueprint;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.defaultapps.blueprint.di.component.AppComponent;
import com.defaultapps.blueprint.di.component.DaggerAppComponent;
import com.defaultapps.blueprint.di.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;


public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLeakDetection();
    }

    @VisibleForTesting
    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        App app = (App) context.getApplicationContext();
        if (app.component == null) {
            app.component = app.createComponent();
        }
        return app.component;
    }

    private void initializeLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
