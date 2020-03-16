package com.example.assignment3.password;

import android.app.Application;
import android.content.Context;

/**
 * @author: LONG, QINGSHENG
 * @ID: 16387388
 */
public class LockApplication extends Application {

    boolean lock;
    @Override
    public void onCreate() {
        super.onCreate();
        lock = true;
    }

    public void setLock(boolean lk){
        lock = lk;
    }

    public boolean getLock(){
        return lock;
    }
}


