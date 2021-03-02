package com.recyclervideoplayer.toro;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.recyclervideoplayer.R;
import com.recyclervideoplayer.adapter.PlayerViewHolder;
import com.recyclervideoplayer.model.MediaObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToroRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MediaObject> mediaObjects;
    private RequestManager requestManager;

    public ToroRecyclerAdapter(List<MediaObject> mediaObjects,
                               RequestManager requestManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SimpleExoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(SimpleExoPlayerViewHolder.LAYOUT_RES, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Uri uri = Uri.parse(mediaObjects.get(i).getUrl());

        ((SimpleExoPlayerViewHolder) viewHolder).bind(this, mediaObjects.get(i), null);
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }
}
