package com.defaultapps.blueprint.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.blueprint.data.entity.PhotoResponse;

import java.util.ArrayList;
import java.util.List;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>  {

    private ArrayList<PhotoResponse> data;

    public PhotosAdapter(List<PhotoResponse> data) {
        this.data.addAll(data);
    }


    static class PhotosViewHolder extends RecyclerView.ViewHolder {

        PhotosViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public PhotosAdapter.PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.PhotosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
