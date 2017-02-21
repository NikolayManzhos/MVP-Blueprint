package com.defaultapps.blueprint.ui.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.defaultapps.blueprint.data.interactor.MainViewInteractor;
import com.defaultapps.blueprint.ui.base.MvpPresenter;
import com.defaultapps.blueprint.ui.fragment.MainViewImpl;

import javax.inject.Inject;


public class MainViewPresenterImpl implements MvpPresenter<MainViewImpl>, MainViewInteractor.MainViewInteractorCallback {

    private MainViewImpl view;
    private MainViewInteractor mainViewInteractor;

    private boolean taskRunning = false;

    @Inject
    public MainViewPresenterImpl(MainViewInteractor mainViewInteractor) {
        this.mainViewInteractor = mainViewInteractor;
        Log.d("PRESENTER", "Constructor");
    }

    @Override
    public void setView(MainViewImpl view) {
        this.view = view;
        mainViewInteractor.bindInteractor(this);
        if (taskRunning && view != null) {
            view.showLoading();
            Log.d("PRESENTER", "LOADING ON SETVIEW");
        }
    }

    @Override
    public void detachView() {
        view = null;
        mainViewInteractor.bindInteractor(null);
    }

    @Override
    public void destroy() {
        //TODO: Stop loading of any data at this point
    }

    public void requestUpdate() {
        taskRunning = true;
        view.showLoading();
        mainViewInteractor.loadData();
    }

    @Override
    public void onSuccess() {
        if (view!= null) {
            view.hideLoading();
            view.hideError();
        }
        taskRunning = false;
    }

    @Override
    public void onFailure() {
        if (view!= null) {
            view.hideLoading();
            view.showError();
        }
        taskRunning = false;
    }


}
