package com.sky.gankmm.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by tonycheng on 2017/2/15.
 */

/**
 * A scoping annotation to permit objects whose lifetime should conform to the life of the Activity
 * to be memorised in the correct component.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
