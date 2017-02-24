package com.defaultapps.blueprint.ui.presenter;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.defaultapps.blueprint.data.interactor.MainViewInteractor;
import com.defaultapps.blueprint.ui.base.MvpPresenter;
import com.defaultapps.blueprint.ui.fragment.MainViewImpl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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
        setTaskStatus(true);
        setErrorVisibilityStatus(false);
        view.showLoading();
        view.hidePhotosList();
        view.hideError();
        mainViewInteractor.loadDataFromNetwork();
    }

    public void requestCachedData() {
        setTaskStatus(true);
        setErrorVisibilityStatus(false);
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
        setTaskStatus(false);
    }

    @Override
    public void onFailure() {
        if (view != null) {
            view.hideLoading();
            view.hidePhotosList();
            view.showError();
        }
        setErrorVisibilityStatus(true);
        setTaskStatus(false);
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

    @VisibleForTesting
    void setTaskStatus(boolean status) {
        this.taskRunning = status;
    }

    @VisibleForTesting
    void setErrorVisibilityStatus(boolean status) {
        this.errorVisible = status;
    }

    public boolean isTaskRunning() {
        return taskRunning;
    }

    public boolean isErrorVisible() {
        return errorVisible;
    }

}
