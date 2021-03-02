package com.recyclervideoplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arthurivanets.arvi.Config;
import com.recyclervideoplayer.adapter.BasicVideoItemViewHolder;
import com.recyclervideoplayer.adapter.ImageHolder;
import com.recyclervideoplayer.adapter.PlayerViewHolder;
import com.recyclervideoplayer.model.MediaObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class BasicVideoItemsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MediaObject> mediaObjects;
    private Config config;

    public BasicVideoItemsRecyclerViewAdapter(Arvictivity arvictivity, List<MediaObject> videos, Config build) {

        this.mediaObjects = videos;
        this.config = build;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_vide, parent, false);

            return new BasicVideoItemViewHolder(parent, view, config);

        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_image_item, parent, false);

        return new ImageHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mediaObjects.get(position).getType() == 0) {

            ((BasicVideoItemViewHolder) holder).bind(mediaObjects.get(position));
        } else {
            ((ImageHolder) holder).bind(mediaObjects.get(position));

        }

    }

    @Override
    public int getItemViewType(int position) {
        return mediaObjects.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }
}
