package com.recyclervideoplayer;

import android.app.Application;
import android.content.Context;

import com.arthurivanets.arvi.PlayerProviderImpl;

import androidx.multidex.MultiDex;

public class App extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level >= TRIM_MEMORY_BACKGROUND) {
            PlayerProviderImpl.getInstance(this).release();
        }
    }
}
