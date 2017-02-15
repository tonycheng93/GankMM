package com.sky.gankmm.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by tonycheng on 2017/2/15.
 */

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * {@link com.sky.gankmm.injection.component.ConfigPersistentComponent}
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
