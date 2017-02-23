package com.defaultapps.blueprint.data.interactor;


import android.os.AsyncTask;
import android.util.Log;

import com.defaultapps.blueprint.data.entity.PhotoResponse;
import com.defaultapps.blueprint.data.local.LocalService;
import com.defaultapps.blueprint.data.net.NetworkService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class MainViewInteractor {

    private AsyncTask<Void, Void, Void> downloadFromNetTask;
    private AsyncTask<Void, Void, Void> loadFromCacheTask;
    private NetworkService networkService;
    private LocalService localService;
    private MainViewInteractorCallback callback;
    private boolean responseStatus;

    private List<PhotoResponse> data;
    private List<String> photosUrl;
    private List<String> photosTitle;


    public interface MainViewInteractorCallback {
        void onSuccess(List<String> photosUrl, List<String> photosTitle);
        void onFailure();
    }


    @Inject
    public MainViewInteractor(NetworkService networkService, LocalService localService) {
        this.networkService = networkService;
        this.localService = localService;
    }

    public void bindInteractor(MainViewInteractorCallback callback) {
        this.callback = callback;
    }


    public void loadDataFromNetwork() {
            downloadFromNetTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Response<List<PhotoResponse>> response = networkService.getNetworkCall().getData().execute();
                        data = response.body();
                        localService.writeResponseToFile(data);
                        if (data != null) {
                            parseData(data);
                        }
                        responseStatus = true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Log.d("AsyncTask", "FAILED TO LOAD OR WRITE DATA");
                        responseStatus = false;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (callback != null) {
                        if (responseStatus) {
                            Log.d("AsyncTaskNet", "SUCCESS");
                            callback.onSuccess(photosUrl, photosTitle);
                            photosUrl = null;
                            photosTitle = null;
                        } else {
                            Log.d("AsyncTaskNet", "FAILURE");
                            callback.onFailure();
                        }
                    }
                }
            };
        if (!downloadFromNetTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            downloadFromNetTask.execute();
        }
    }

    public void loadDataFromCache() {
        loadFromCacheTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    data = localService.readResponseFromFile();
                    parseData(data);
                    responseStatus = true;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Log.d("AsyncTaskLocal", "FAILED TO READ DATA");
                    responseStatus = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (callback != null) {
                    if (responseStatus) {
                        Log.d("AsyncTaskLocal", "SUCCESS");
                        callback.onSuccess(photosUrl, photosTitle);
                        photosUrl = null;
                        photosTitle = null;
                    } else {
                        Log.d("AsyncTaskLocal", "FAILURE");
                        callback.onFailure();
                    }
                }
            }
        };
        if (!loadFromCacheTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            loadFromCacheTask.execute();
        }
    }

    private void parseData(List<PhotoResponse> dataToParse) {
        photosUrl = new ArrayList<>();
        photosTitle = new ArrayList<>();
        for (PhotoResponse photoEntity : dataToParse ) {
            photosUrl.add(photoEntity.getUrl());
            photosTitle.add(photoEntity.getTitle());
        }
    }
}
