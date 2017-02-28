package com.sky.gankmm.data;

import com.google.gson.Gson;
import com.sky.gankmm.data.local.DatabaseHelper;
import com.sky.gankmm.data.local.PreferencesHelper;
import com.sky.gankmm.data.model.Gank;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.data.remote.GankService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by tonycheng on 2017/2/14.
 */
@Singleton
public class DataManager {

    private final GankService mGankService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(GankService gankService, DatabaseHelper databaseHelper, PreferencesHelper preferencesHelper) {
        mGankService = gankService;
        mDatabaseHelper = databaseHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Result> syncGank() {
        return mGankService.getGanks()
                .concatMap(new Func1<Gank, Observable<? extends Result>>() {
                    @Override
                    public Observable<? extends Result> call(Gank gank) {
                        Timber.i(new Gson().toJson(gank.results()));
                        return mDatabaseHelper.setGanks(gank.results());
                    }
                });
    }

    public Observable<List<Result>> getGanks() {
        return mDatabaseHelper.getGanks().distinct();
    }
}
