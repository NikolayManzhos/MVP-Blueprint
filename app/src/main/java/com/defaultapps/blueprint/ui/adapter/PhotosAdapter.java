package com.defaultapps.blueprint.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.defaultapps.blueprint.R;
import com.defaultapps.blueprint.data.entity.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>  {

    private ArrayList<String> photosUrl;
    private ArrayList<String> photosTitle;
    private Context context;

    @Inject
    public PhotosAdapter(Context context) {
        this.context = context;
//        this.data.addAll(data);
    }


    static class PhotosViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photoImage)
        ImageView photoImage;

        @BindView(R.id.photoText)
        TextView photoText;


        PhotosViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public PhotosAdapter.PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.PhotosViewHolder holder, int position) {
        if (photosUrl != null && photosTitle != null) {
            holder.photoText.setText(photosTitle.get(holder.getAdapterPosition()));
            Glide
                    .with(context)
                    .load(photosUrl.get(holder.getAdapterPosition()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.photoImage);
        }
    }

    @Override
    public int getItemCount() {
        return photosUrl != null ? photosUrl.size() : 0;
    }


    public void setPhotosData(List<String> photosUrl, List<String> photosTitle) {
        this.photosUrl = new ArrayList<>(photosUrl);
        this.photosTitle = new ArrayList<>(photosTitle);
        notifyDataSetChanged();
    }
}
