package com.defaultapps.blueprint.ui.fragment;

import com.defaultapps.blueprint.ui.base.MvpView;

import java.util.List;


public interface MainView extends MvpView {
    void updateView(List<String> photosUrl, List<String> photos);
    void hidePhotosList();
    void showPhotosList();
}
