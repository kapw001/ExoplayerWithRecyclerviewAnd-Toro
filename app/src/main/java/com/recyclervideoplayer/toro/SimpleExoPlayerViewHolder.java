package com.recyclervideoplayer.toro;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.PlayerView;
import com.recyclervideoplayer.R;
import com.recyclervideoplayer.model.MediaObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class SimpleExoPlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

    static final int LAYOUT_RES = R.layout.vh_exoplayer_basic;

    @Nullable
    ExoPlayerViewHelper helper;
    EventListener listener;
    @Nullable
    private Uri mediaUri;

    //    @BindView(R.id.player)
    PlayerView playerView;
    private ImageView imageView;
    private ProgressBar progressBar;

    SimpleExoPlayerViewHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this, itemView);

        playerView = itemView.findViewById(R.id.player);

        progressBar = itemView.findViewById(R.id.progress_bar);
        imageView = itemView.findViewById(R.id.img);

        playerView.setShutterBackgroundColor(Color.TRANSPARENT);


    }

    // called from Adapter to setup the media
    void bind(@NonNull RecyclerView.Adapter adapter, MediaObject item, List<Object> payloads) {
        if (item != null) {
            mediaUri = Uri.parse(item.getUrl());

            Glide.with(imageView.getContext())
                    .load(item.getCoverUrl())
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
//            playerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        if (!isPlaying()) {
            progressBar.setVisibility(View.GONE);
        }
    }

//    // called from Adapter to setup the media
//    void bind(@NonNull RecyclerView.Adapter adapter, Uri item, List<Object> payloads) {
//        if (item != null) {
//            mediaUri = item;
//        }
//    }

    @NonNull
    @Override
    public View getPlayerView() {
        return playerView;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    private static final String TAG = "SimpleExoPlayerViewHold";

    @Override
    public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new ExoPlayerViewHelper(this, mediaUri);
        }


        if (listener == null) {
            listener = new EventListener() {
                @Override
                public void onFirstFrameRendered() {

//                    playerView.setVisibility(View.GONE);
//                    imageView.setVisibility(View.INVISIBLE);
//                    playerView.setVisibility(View.VISIBLE);
//                    playerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onBuffering() {
//                    playerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
//                    playerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPlaying() {
//
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    playerView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onPaused() {

                }

                @Override
                public void onCompleted() {
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            };
            helper.addPlayerEventListener(listener);
        }

        helper.initialize(container, playbackInfo);

    }

    @Override
    public void release() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        if (helper != null) {

            if (listener != null) {
                helper.removePlayerEventListener(listener);
                listener = null;
            }

            helper.release();
            helper = null;
        }
    }

    @Override
    public void play() {
        if (helper != null) helper.play();
    }

    @Override
    public void pause() {
        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public boolean wantsToPlay() {
//        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 1;
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
//        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.65;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }
}