package com.recyclervideoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.recyclervideoplayer.model.MediaObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private VideoAdapter mVideoAdapter;

    private LinearLayoutManager manager;
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private int playPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.videoListView);

        mVideoAdapter = new VideoAdapter(this, prepareVideoList());

        manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mVideoAdapter);


        Display display = getWindowManager().getDefaultDisplay();//.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            private int mFirstVisibleRow = -1;
//            private int mActiveItem = -1;
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int firstVisibleRow = manager.findFirstVisibleItemPosition();
//                if (mFirstVisibleRow != firstVisibleRow) {
//                    mFirstVisibleRow = firstVisibleRow;
//
//                    // Cancel the video of the previous active item
//                    SimpleExoPlayer prevActiveVideoView = getVideoViewForRow(mActiveItem);
//                    if (prevActiveVideoView != null) {
//                        prevActiveVideoView.setPlayWhenReady(false);
//                    }
//
//                    // Start the video of the new active item
//                    mActiveItem = mFirstVisibleRow + 1;
//                    SimpleExoPlayer newActiveVideoView = getVideoViewForRow(mActiveItem);
//                    if (newActiveVideoView != null) {
//                        newActiveVideoView.setPlayWhenReady(true);
//                    }
//                }
//            }
//        }


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int targetPosition;
                int startPosition = manager.findFirstVisibleItemPosition();
                int endPosition = manager.findLastVisibleItemPosition();

                // if there is more than 2 list-items on the screen, set the difference to be 1
                if (endPosition - startPosition > 1) {
                    endPosition = startPosition + 1;
                }

                // something is wrong. return.
                if (startPosition < 0 || endPosition < 0) {
                    return;
                }

                // if there is more than 1 list-item on the screen
                if (startPosition != endPosition) {
                    int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                    int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);

                    targetPosition =
                            startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
                } else {
                    targetPosition = startPosition;
                }

                // video is already playing so return
                if (targetPosition == playPosition)

                {
                    return;
                }

                // set the position of the list-item that is to be played
                playPosition = targetPosition;

                mVideoAdapter.setPlayPosition(playPosition);


            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {


                }

            }
        });


    }

    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - manager.findFirstVisibleItemPosition();
//        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: " + at);

        View child = recyclerView.getChildAt(at);
        if (child == null) {
            return 0;
        }

        int[] location = new int[2];
        child.getLocationInWindow(location);

        if (location[1] < 0) {
            return location[1] + videoSurfaceDefaultHeight;
        } else {
            return screenDefaultHeight - location[1];
        }
    }

//    private SimpleExoPlayer getVideoViewForRow(int row) {
//        int firstVisibleRow = manager.findFirstVisibleItemPosition();
//        View rowView = recyclerView.getChildAt(row - firstVisibleRow);
//        PlayerView playerView = (rowView == null) ? null : (PlayerView) rowView.findViewById(R.id.playerview);
//        if (rowView != null) {
//            return (SimpleExoPlayer) playerView.getTag();
//        }
//        return null;
////        return (rowView == null) ? null : (PlayerView) rowView.findViewById(R.id.playerview);
//    }


    private List<MediaObject> prepareVideoList() {

        List<MediaObject> mediaObjectList = new ArrayList<>();

        mediaObjectList.add(new MediaObject("Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1"));
        mediaObjectList.add(new MediaObject("REST API, Retrofit2, MVVM Course SUMMARY",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                "Description for media object #2"));
        mediaObjectList.add(new MediaObject("MVVM and LiveData",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/MVVM+and+LiveData+for+youtube.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png",
                "Description for media object #3"));
        mediaObjectList.add(new MediaObject("Swiping Views with a ViewPager",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/SwipingViewPager+Tutorial.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                "Description for media object #4"));
        mediaObjectList.add(new MediaObject("Database Cache, MVVM, Retrofit, REST API demo for upcoming course",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                "Description for media object #5"));

        return mediaObjectList;
    }
}
