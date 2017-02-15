package com.sky.gankmm.injection.module;

import android.app.Activity;
import android.content.Context;

import com.sky.gankmm.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tonycheng on 2017/2/15.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity){
        mActivity = activity;
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }
}
