package com.defaultapps.blueprint.data.local;


import android.content.Context;

import com.defaultapps.blueprint.data.entity.PhotoResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public void writeResponseToFile(List<PhotoResponse> responseList) {
        try {
            BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(file));
            bs.write(gson.toJson(responseList).getBytes());
            bs.flush();
            bs.close();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

    /**
     * Read response from file.
     * Do not execute on the UI thread!
     * @return
     */
    public List<PhotoResponse> readResponseFromFile() throws IOException {
        Type listOfPhotoResponseType = new TypeToken<ArrayList<PhotoResponse>>(){}.getType();
        int length = (int) file.length();
        byte[] bytes = new byte[length];
            BufferedInputStream bs = new BufferedInputStream(new FileInputStream(file));
            bs.read(bytes, 0, bytes.length);
            bs.close();

        String jsonString = new String(bytes);
        return gson.fromJson(jsonString, listOfPhotoResponseType);
    }

}
