package com.recyclervideoplayer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.recyclervideoplayer.adapter.MediaRecyclerAdapter;
import com.recyclervideoplayer.adapter.MultiTypeMediaRecyclerAdapter;
import com.recyclervideoplayer.model.MediaObject;
import com.recyclervideoplayer.ui.ExoPlayerRecyclerView;
import com.recyclervideoplayer.utils.DividerItemDecoration;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoUiActivity extends AppCompatActivity {

    ExoPlayerRecyclerView mRecyclerView;

    private ArrayList<MediaObject> mediaObjectList = new ArrayList<>();
    private MultiTypeMediaRecyclerAdapter mAdapter;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        // Prepare demo content
        prepareVideoList();

        //set data object
        mRecyclerView.setMediaObjects(mediaObjectList);
        mAdapter = new MultiTypeMediaRecyclerAdapter(mediaObjectList, initGlide());

        //Set Adapter
        mRecyclerView.setAdapter(mAdapter);

        if (firstTime) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.playVideo(false);
                }
            });
            firstTime = false;
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.exoPlayerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRecyclerView != null) {
            mRecyclerView.onPausePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRecyclerView != null) {
            mRecyclerView.onResumePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView.releasePlayer();
        }
        super.onDestroy();
    }

    private void prepareVideoList() {
//        MediaObject mediaObject = new MediaObject();
//        mediaObject.setId(1);
//        mediaObject.setUserHandle("@h.pandya");
//        mediaObject.setTitle(
//                "Do you think the concept of marriage will no longer exist in the future?");
//        mediaObject.setCoverUrl(
//                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-1.png");
//        mediaObject.setUrl("https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4");
//
//        MediaObject mediaObject2 = new MediaObject();
//        mediaObject2.setId(2);
//        mediaObject2.setUserHandle("@hardik.patel");
//        mediaObject2.setTitle(
//                "If my future husband doesn't cook food as good as my mother should I scold him?");
//        mediaObject2.setCoverUrl(
//                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-2.png");
//        mediaObject2.setUrl("https://androidwave.com/media/androidwave-video-2.mp4");
//
//        MediaObject mediaObject3 = new MediaObject();
//        mediaObject3.setId(3);
//        mediaObject3.setUserHandle("@arun.gandhi");
//        mediaObject3.setTitle("Give your opinion about the Ayodhya temple controversy.");
//        mediaObject3.setCoverUrl(
//                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-3.png");
//        mediaObject3.setUrl("https://androidwave.com/media/androidwave-video-3.mp4");
//
//        MediaObject mediaObject4 = new MediaObject();
//        mediaObject4.setId(4);
//        mediaObject4.setUserHandle("@sachin.patel");
//        mediaObject4.setTitle("When did kama founders find sex offensive to Indian traditions");
//        mediaObject4.setCoverUrl(
//                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-4.png");
//        mediaObject4.setUrl("https://androidwave.com/media/androidwave-video-6.mp4");
//
//        MediaObject mediaObject5 = new MediaObject();
//        mediaObject5.setId(5);
//        mediaObject5.setUserHandle("@monika.sharma");
//        mediaObject5.setTitle("When did you last cry in front of someone?");
//        mediaObject5.setCoverUrl(
//                "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-5.png");
//        mediaObject5.setUrl("https://androidwave.com/media/androidwave-video-5.mp4");

        mediaObjectList.add(new MediaObject("Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1"));

        mediaObjectList.add(new MediaObject("Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1", 1));
        mediaObjectList.add(new MediaObject("REST API, Retrofit2, MVVM Course SUMMARY",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                "Description for media object #2", 1));

        mediaObjectList.add(new MediaObject("REST API, Retrofit2, MVVM Course SUMMARY",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                "Description for media object #2"));
        mediaObjectList.add(new MediaObject("Swiping Views with a ViewPager",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/SwipingViewPager+Tutorial.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                "Description for media object #4", 1));
        mediaObjectList.add(new MediaObject("Database Cache, MVVM, Retrofit, REST API demo for upcoming course",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                "Description for media object #5", 1));
        mediaObjectList.add(new MediaObject("Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1", 1));

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
//        mediaObjectList.add(mediaObject);
//        mediaObjectList.add(mediaObject2);
//        mediaObjectList.add(mediaObject3);
//        mediaObjectList.add(mediaObject4);
//        mediaObjectList.add(mediaObject5);
    }


}
