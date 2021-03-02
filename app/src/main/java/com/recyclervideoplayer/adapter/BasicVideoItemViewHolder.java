package com.recyclervideoplayer.adapter;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arthurivanets.arvi.Config;
import com.arthurivanets.arvi.widget.PlayableItemViewHolder;
import com.arthurivanets.arvi.widget.PlaybackState;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.recyclervideoplayer.R;
import com.recyclervideoplayer.model.MediaObject;

import androidx.annotation.NonNull;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BasicVideoItemViewHolder extends PlayableItemViewHolder {

    private String url;
    private Config config;

    private ImageView imageView;
    private ProgressBar progressBar;

    public BasicVideoItemViewHolder(ViewGroup parentViewGroup, View itemView, Config config) {
        super(parentViewGroup, itemView);
        this.config = config;

        imageView = itemView.findViewById(R.id.image);
        progressBar = itemView.findViewById(R.id.progress_bar);

        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

    }


//    public void bindData(MediaObject video){
//        handleData(video.getUrl());
//        video=video;
//    }
//
//    public void handleData(String video){
//        // video=video;
//    }

    @NonNull
    @Override
    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public Config getConfig() {
        return config;
    }


    public void bind(MediaObject mediaObject) {
        this.url = mediaObject.getUrl();

        Glide.with(imageView.getContext())
                .load(mediaObject.getCoverUrl())
                .into(imageView);
    }

    @Override
    protected void onStateChanged(@NonNull PlaybackState playbackState) {
        super.onStateChanged(playbackState);


        switch (playbackState) {

            case STARTED:

                mPlayerView.setVisibility(VISIBLE);

                break;

            case BUFFERING:

                if (progressBar != null) {
                    progressBar.setVisibility(VISIBLE);
                }

                break;
            case ERROR:
                mPlayerView.setVisibility(View.INVISIBLE);
                break;

            case READY:
                mPlayerView.setVisibility(VISIBLE);
                if (progressBar != null) {
                    progressBar.setVisibility(GONE);
                }
                break;

        }

    }

    @Override
    public boolean isLooping() {
        return true;
    }
}
