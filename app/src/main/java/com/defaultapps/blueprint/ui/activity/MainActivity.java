package com.defaultapps.blueprint.ui.activity;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.defaultapps.blueprint.R;
import com.defaultapps.blueprint.ui.fragment.MainViewImpl;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    
    @Inject
    Application application;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame, new MainViewImpl())
                    .commit();
        }
    }
}
