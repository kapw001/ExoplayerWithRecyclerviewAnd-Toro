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
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private static final String TAG = "VideoAdapter";
    private List<MediaObject> mList;
    private SimpleExoPlayer videoPlayer;
    private Context mContext;
    private int playPosition = 0;

    public VideoAdapter(Context context, List<MediaObject> mList) {
        this.mList = mList;
        this.mContext = context;

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_test, parent, false);


        return new VideoHolder(view, videoPlayer);
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


        Log.e(TAG, "onBindViewHolder: " + playPosition);

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

        public VideoHolder(@NonNull View itemView, SimpleExoPlayer simpleExoPlayer) {
            super(itemView);
            this.videoPlayer = simpleExoPlayer;
            playerView = itemView.findViewById(R.id.playerview);
            progressBar = itemView.findViewById(R.id.progress_bar);
            imageView = itemView.findViewById(R.id.image);

            playerView.setUseController(false);
            playerView.setPlayer(videoPlayer);

        }


        public void bind(MediaObject mediaObject) {

            Glide.with(itemView.getContext())
                    .load(mediaObject.getCoverUrl()).into(imageView);

        }

        public void play(MediaObject mediaObject) {

            videoPlayer.addListener(new VideoListener() {


                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                    switch (playbackState) {
                        case Player.STATE_BUFFERING:
//                            Log.e(TAG, "onPlayerStateChanged: Buffering video.");
                            if (progressBar != null) {
                                progressBar.setVisibility(VISIBLE);
                            }
                            break;
                        case Player.STATE_ENDED:
//                            Log.d(TAG, "onPlayerStateChanged: Video ended.");
                            videoPlayer.seekTo(0);
                            break;
                        case Player.STATE_IDLE:
                            break;
                        case Player.STATE_READY:
//                            Log.e(TAG, "onPlayerStateChanged: Ready to play.");
                            if (progressBar != null) {
                                progressBar.setVisibility(GONE);
                            }

                            imageView.setVisibility(GONE);
                            playerView.setVisibility(VISIBLE);

                            break;
                        default:
                            break;
                    }

                }


            });


            playerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {

                }

                @Override
                public void onViewDetachedFromWindow(View view) {

                    videoPlayer.stop();
//                    videoPlayer.release();
//                    videoPlayer.addListener(null);
//                    playerView.setVisibility(View.GONE);


                }
            });

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    itemView.getContext(), Util.getUserAgent(itemView.getContext(), "RecyclerView VideoPlayer"));

            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mediaObject.getUrl()));
            videoPlayer.prepare(videoSource);
            videoPlayer.setPlayWhenReady(true);

        }
    }


}
