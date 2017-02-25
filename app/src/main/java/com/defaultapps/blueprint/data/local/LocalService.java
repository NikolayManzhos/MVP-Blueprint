package com.defaultapps.blueprint.data.local;


import android.content.Context;
import android.provider.MediaStore;

import com.defaultapps.blueprint.data.entity.PhotoResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LocalService {

    private final String FILE_NAME = "response.cache";
    private final Gson gson = new Gson();
    private final File file;

    @Inject
    public LocalService(Context context) {
        file = new File(context.getCacheDir(), FILE_NAME);
    }

    /**
     * Writes response from api to file.
     * Do not execute on the UI thread!
     * @param responseList List of PhotoResponse entities.
     */
    public void writeResponseToFile(List<PhotoResponse> responseList) throws IOException {
        BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(file));
        bs.write(gson.toJson(responseList).getBytes());
        bs.flush();
        bs.close();
    }

    /**
     * Read response from file.
     * Do not execute on the UI thread!
     */
    public List<PhotoResponse> readResponseFromFile() throws IOException {
        Type listOfPhotoResponseType = new TypeToken<ArrayList<PhotoResponse>>(){}.getType();
        String line = "";
        StringBuilder jsonString = new StringBuilder();

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            jsonString.append(line);
        }
        return gson.fromJson(jsonString.toString(), listOfPhotoResponseType);
    }

    public void deleteCache() {
        if (isCacheAvailable()) {
            file.delete();
        }
    }

    public boolean isCacheAvailable() {
        return file.exists();
    }

}
