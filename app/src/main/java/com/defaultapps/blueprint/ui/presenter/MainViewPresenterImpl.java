package com.defaultapps.blueprint.ui.presenter;

import android.util.Log;

import com.defaultapps.blueprint.data.entity.PhotoResponse;
import com.defaultapps.blueprint.data.interactor.MainViewInteractor;
import com.defaultapps.blueprint.ui.base.MvpPresenter;
import com.defaultapps.blueprint.ui.fragment.MainViewImpl;

import java.util.List;

import javax.inject.Inject;


public class MainViewPresenterImpl implements MvpPresenter<MainViewImpl>, MainViewInteractor.MainViewInteractorCallback {

    private MainViewImpl view;
    private MainViewInteractor mainViewInteractor;

    private boolean taskRunning = false;
    private boolean errorVisible = false;

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
        //TODO: Stop loading of any data at this point ?
    }

    public void requestUpdate() {
        taskRunning = true;
        errorVisible = false;
        view.showLoading();
        view.hidePhotosList();
        view.hideError();
        mainViewInteractor.loadDataFromNetwork();
    }

    public void requestCachedData() {
        taskRunning = true;
        errorVisible = false;
        view.showLoading();
        mainViewInteractor.loadDataFromCache();
    }

    @Override
    public void onSuccess(List<String> photosUrl, List<String> photosTitle) {
        if (view != null) {
            view.hideLoading();
            view.hideError();
            view.showPhotosList();
            view.updateView(photosUrl, photosTitle);
        }
        taskRunning = false;
    }

    @Override
    public void onFailure() {
        if (view != null) {
            view.hideLoading();
            view.showError();
            view.hidePhotosList();
        }
        errorVisible = true;
        taskRunning = false;
    }

    public void restoreViewState() {
        if (view != null) {
            if (taskRunning) {
                view.showLoading();
                view.hidePhotosList();
                view.hideError();
            } else if(errorVisible) {
                view.showError();
                view.hidePhotosList();
                view.hideLoading();
            } else {
                requestCachedData();
            }
        }
    }

    public boolean isTaskRunning() {
        return taskRunning;
    }

    public boolean isErrorVisible() {
        return errorVisible;
    }

}
