package com.defaultapps.blueprint.data.interactor;


import android.os.AsyncTask;

import javax.inject.Inject;

public class MainViewInteractor {

    private MainViewInteractorCallback callback;

    public interface MainViewInteractorCallback {
        void onSuccess();
        void onFailure();
    }


    @Inject
    public MainViewInteractor() {}

    public void bindInteractor(MainViewInteractorCallback callback) {
        this.callback = callback;
    }


    public void loadData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (callback != null) {
                    callback.onSuccess();
                }
            }
        }.execute();
    }
}
