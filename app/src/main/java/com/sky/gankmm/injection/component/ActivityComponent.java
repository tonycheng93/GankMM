package com.sky.gankmm.injection.component;

import com.sky.gankmm.injection.PerActivity;
import com.sky.gankmm.injection.module.ActivityModule;
import com.sky.gankmm.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by tonycheng on 2017/2/15.
 */

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
