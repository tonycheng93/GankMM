package com.sky.gankmm.injection.component;

import com.sky.gankmm.data.DataManager;
import com.sky.gankmm.data.local.DatabaseHelper;
import com.sky.gankmm.data.local.PreferencesHelper;
import com.sky.gankmm.util.RxBus;

import javax.inject.Singleton;

/**
 * Created by tonycheng on 2017/2/14.
 */
@Singleton
public interface ApplicationComponent {

    DataManager dataManager();

    DatabaseHelper databaseHelper();

    PreferencesHelper preferencesHelper();

    RxBus rxBus();
}