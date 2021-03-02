package com.recyclervideoplayer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.recyclervideoplayer.model.MediaObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VideoAdapterBackup extends RecyclerView.Adapter<VideoAdapterBackup.VideoHolder> {

    private static final String TAG = "VideoAdapter";
    private List<MediaObject> mList;
    private SimpleExoPlayer videoPlayer;
    private PlayerView playerView;
    private Context mContext;
    private int playPosition = 0;

    public VideoAdapterBackup(Context context, List<MediaObject> mList) {
        this.mList = mList;
        this.mContext = context;

        playerView = new PlayerView(mContext);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        // Disable Player Control
        playerView.setUseController(false);
        // Bind the player to the view.
        playerView.setPlayer(videoPlayer);
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);


        return new VideoHolder(view, videoPlayer, playerView);
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        holder.bind(mList.get(position));
        if (playPosition == position) {

            holder.play(mList.get(position));

//            holder.videoPlayer.setPlayWhenReady(true);

        } else {
//            holder.videoPlayer.stop();
//            holder.frameLayout.removeView(playerView);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


//    @Override
//    public void onViewDetachedFromWindow(@NonNull VideoHolder holder) {
//
//        holder.videoPlayer.stop();
//        holder.videoPlayer.release();
////        holder.videoPlayer = null;
//
//        super.onViewDetachedFromWindow(holder);
//    }

    public static class VideoHolder extends RecyclerView.ViewHolder {


        SimpleExoPlayer videoPlayer;
        ImageView imageView;
        FrameLayout frameLayout;
        PlayerView playerView;
        ProgressBar progressBar;

        public VideoHolder(@NonNull View itemView, SimpleExoPlayer simpleExoPlayer, PlayerView playerView) {
            super(itemView);
            this.videoPlayer = simpleExoPlayer;
            this.playerView = playerView;
//            playerView = itemView.findViewById(R.id.playerview);
            progressBar = itemView.findViewById(R.id.progress_bar);
            imageView = itemView.findViewById(R.id.image);
            frameLayout = itemView.findViewById(R.id.frame);

            frameLayout.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {

                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    frameLayout.removeAllViews();
//                    ViewGroup parent = (ViewGroup) holder.frameLayout.getParent();
//                    if (parent == null) {
//                        return;
//                    }
//
//                    int index = parent.indexOfChild(holder.frameLayout);
//                    if (index >= 0) {
//                        parent.removeViewAt(index);
////                    isVideoViewAdded = false;
////                    viewHolderParent.setOnClickListener(null);
//                    }

                }
            });

        }


        public void bind(MediaObject mediaObject) {

            Glide.with(itemView.getContext())
                    .load(mediaObject.getCoverUrl()).into(imageView);

//            if (mediaObject.getUrl() != null) {
//                // Bind the player to the view.
//
//                videoPlayer.addListener(new Player.EventListener() {
//                    @Override
//                    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
//
//                    }
//
//                    @Override
//                    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//                    }
//
//                    @Override
//                    public void onLoadingChanged(boolean isLoading) {
//
//                    }
//
//                    @Override
//                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                        switch (playbackState) {
//                            case Player.STATE_BUFFERING:
//                                Log.e(TAG, "onPlayerStateChanged: Buffering video.");
//                                if (progressBar != null) {
//                                    progressBar.setVisibility(VISIBLE);
//                                }
//                                break;
//                            case Player.STATE_ENDED:
//                                Log.d(TAG, "onPlayerStateChanged: Video ended.");
//                                videoPlayer.seekTo(0);
//                                break;
//                            case Player.STATE_IDLE:
//                                break;
//                            case Player.STATE_READY:
//                                Log.e(TAG, "onPlayerStateChanged: Ready to play.");
//                                if (progressBar != null) {
//                                    progressBar.setVisibility(GONE);
//                                }
//
//                                if (playerView.getParent() != null) {
//                                    ((ViewGroup) playerView.getParent()).removeView(playerView);
//                                }
////                                frameLayout.removeAllViews();
//                                frameLayout.addView(playerView);
//
//                                break;
//                            default:
//                                break;
//                        }
//
//                    }
//
//                    @Override
//                    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
//
//                    }
//
//                    @Override
//                    public void onIsPlayingChanged(boolean isPlaying) {
//
//                    }
//
//                    @Override
//                    public void onRepeatModeChanged(int repeatMode) {
//
//                    }
//
//                    @Override
//                    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//
//                    }
//
//                    @Override
//                    public void onPlayerError(ExoPlaybackException error) {
//
//                    }
//
//                    @Override
//                    public void onPositionDiscontinuity(int reason) {
//
//                    }
//
//                    @Override
//                    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//                    }
//
//                    @Override
//                    public void onSeekProcessed() {
//
//                    }
//                });
//
//
////                playerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
////                    @Override
////                    public void onViewAttachedToWindow(View view) {
////
////                    }
////
////                    @Override
////                    public void onViewDetachedFromWindow(View view) {
////
////                        videoPlayer.stop();
////
////
////                    }
////                });
//
//                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
//                        itemView.getContext(), Util.getUserAgent(itemView.getContext(), "RecyclerView VideoPlayer"));
//
//                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
//                        .createMediaSource(Uri.parse(mediaObject.getUrl()));
//                videoPlayer.prepare(videoSource);
//                videoPlayer.setPlayWhenReady(true);


//            }

        }

        public void play(MediaObject mediaObject) {

            videoPlayer.addListener(new VideoListener() {


                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                    switch (playbackState) {
                        case Player.STATE_BUFFERING:
                            Log.e(TAG, "onPlayerStateChanged: Buffering video.");
                            if (progressBar != null) {
                                progressBar.setVisibility(VISIBLE);
                            }
                            break;
                        case Player.STATE_ENDED:
                            Log.d(TAG, "onPlayerStateChanged: Video ended.");
                            videoPlayer.seekTo(0);
                            break;
                        case Player.STATE_IDLE:
                            break;
                        case Player.STATE_READY:
                            Log.e(TAG, "onPlayerStateChanged: Ready to play.");
                            if (progressBar != null) {
                                progressBar.setVisibility(GONE);
                            }

                            if (playerView.getParent() != null) {
                                ((ViewGroup) playerView.getParent()).removeView(playerView);
                            }
////                                frameLayout.removeAllViews();
                            frameLayout.addView(playerView);

                            break;
                        default:
                            break;
                    }

                }


            });


//                playerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//                    @Override
//                    public void onViewAttachedToWindow(View view) {
//
//                    }
//
//                    @Override
//                    public void onViewDetachedFromWindow(View view) {
//
//                        videoPlayer.stop();
//
//
//                    }
//                });

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    itemView.getContext(), Util.getUserAgent(itemView.getContext(), "RecyclerView VideoPlayer"));

            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mediaObject.getUrl()));
            videoPlayer.prepare(videoSource);
            videoPlayer.setPlayWhenReady(true);

        }
    }


}
