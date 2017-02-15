package com.sky.gankmm;

import android.app.Application;
import android.content.Context;

import com.sky.gankmm.injection.component.ApplicationComponent;
import com.sky.gankmm.injection.component.DaggerApplicationComponent;
import com.sky.gankmm.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by tonycheng on 2017/2/15.
 */

public class GankApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static GankApplication get(Context context) {
        return (GankApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
