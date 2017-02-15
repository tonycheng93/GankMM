package com.sky.gankmm.injection.component;

import com.sky.gankmm.injection.ConfigPersistent;
import com.sky.gankmm.injection.module.ActivityModule;

import dagger.Component;

/**
 * Created by tonycheng on 2017/2/15.
 */


/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check {@link com.sky.gankmm.ui.base.BaseActivity} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
