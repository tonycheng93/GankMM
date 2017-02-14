package com.sky.gankmm.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.sky.gankmm.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by tonycheng on 2017/2/14.
 */
@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NMAE = "gank_pref_file";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NMAE, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
