package com.recyclervideoplayer.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.recyclervideoplayer.R;
import com.recyclervideoplayer.model.MediaObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageHolder extends RecyclerView.ViewHolder {


    ImageView imageView;

    public ImageHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.img);

    }

    public void bind(MediaObject mediaObject) {

        Glide.with(imageView.getContext())
                .load(mediaObject.getCoverUrl())
                .into(imageView);

    }
}
