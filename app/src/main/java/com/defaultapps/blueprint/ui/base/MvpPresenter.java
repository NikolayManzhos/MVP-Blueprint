package com.defaultapps.blueprint.ui.base;


public interface MvpPresenter<T> {
    void setView(T view);
    void detachView();
    void destroy();
}
