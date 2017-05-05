package com.sky.gankmm.injection.component;

import android.app.Application;
import android.content.Context;

import com.sky.gankmm.data.DataManager;
import com.sky.gankmm.data.SyncService;
import com.sky.gankmm.data.local.DatabaseHelper;
import com.sky.gankmm.data.local.PreferencesHelper;
import com.sky.gankmm.data.remote.GankHttpService;
import com.sky.gankmm.data.remote.GankService;
import com.sky.gankmm.injection.ApplicationContext;
import com.sky.gankmm.injection.module.ApplicationModule;
import com.sky.gankmm.util.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tonycheng on 2017/2/14.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();

    Application application();

    GankService gankService();

    GankHttpService gankHttpService();

    DataManager dataManager();

    DatabaseHelper databaseHelper();

    PreferencesHelper preferencesHelper();

    RxBus rxBus();
}