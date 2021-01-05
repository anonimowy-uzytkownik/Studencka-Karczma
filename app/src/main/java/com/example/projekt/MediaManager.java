package com.example.projekt;

import android.content.Context;

public class MediaManager {

    /**
     * SingleTon instance
     */
    private static MediaManager sInstance;

    private Context mContext;


    private MediaManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static MediaManager getInstance(Context context) {
        if (null == sInstance) {
            synchronized (MediaManager.class) {
                sInstance = new MediaManager(context);
            }
        }
        return sInstance;
    }
}