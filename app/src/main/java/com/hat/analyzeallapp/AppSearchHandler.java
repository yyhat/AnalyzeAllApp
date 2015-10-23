package com.hat.analyzeallapp;

import android.os.Handler;
import android.os.Message;

/**
 * Created by anting.hu on 2015/10/23.
 */
public class AppSearchHandler extends Handler {

    public static final int SEARCH_FINISH = 1;
    private AppSearchCallback mCallback;
    public AppSearchHandler(AppSearchCallback callback)
    {
        mCallback = callback;
    }
    @Override
    public void handleMessage(Message msg) {
        if(msg.what == SEARCH_FINISH)
        {
            mCallback.OnFinish();
        }
    }
}
