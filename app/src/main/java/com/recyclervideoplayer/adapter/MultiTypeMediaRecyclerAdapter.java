package com.recyclervideoplayer.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.recyclervideoplayer.R;
import com.recyclervideoplayer.model.MediaObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MultiTypeMediaRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MediaObject> mediaObjects;
    private RequestManager requestManager;

    public MultiTypeMediaRecyclerAdapter(ArrayList<MediaObject> mediaObjects,
                                         RequestManager requestManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == 0) {

            return new PlayerViewHolder(
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.layout_media_list_item, viewGroup, false));

        }

        return new ImageHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_image_item, viewGroup, false));

    }

    @Override
    public int getItemViewType(int position) {
        return mediaObjects.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (mediaObjects.get(i).getType() == 0) {
            ((PlayerViewHolder) viewHolder).onBind(mediaObjects.get(i), requestManager);
        } else {

            ((ImageHolder) viewHolder).bind(mediaObjects.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }
}
