package com.sky.gankmm.injection.module;

/**
 * Created by tonycheng on 2017/2/15.
 */

import android.app.Application;
import android.content.Context;

import com.sky.gankmm.data.remote.GankHttpService;
import com.sky.gankmm.data.remote.GankService;
import com.sky.gankmm.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context context() {
        return mApplication;
    }

    @Provides
    @Singleton
    GankService provideGankService() {
        return GankService.Creator.newGankService();
    }

    @Provides
    @Singleton
    GankHttpService provideGankHttpService(){
        return null;
    }
}
