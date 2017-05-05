package com.sky.gankmm.data;

import com.google.gson.Gson;

import com.sky.gankmm.data.local.DatabaseHelper;
import com.sky.gankmm.data.local.PreferencesHelper;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.data.remote.GankHttpService;
import com.sky.gankmm.data.remote.GankService;
import com.sky.gankmm.http.core.HttpListResult;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by tonycheng on 2017/2/14.
 */
@Singleton
public class DataManager {

    private final GankHttpService mGankHttpService;
    private final GankService mGankService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(GankHttpService gankHttpService, GankService gankService, DatabaseHelper databaseHelper, PreferencesHelper preferencesHelper) {
        mGankHttpService = gankHttpService;
        mGankService = gankService;
        mDatabaseHelper = databaseHelper;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

//    public Flowable<Result> syncGank() {
//        return mGankService.getGanks()
//                .concatMap(new Func1<Gank, Observable<? extends Result>>() {
//                    @Override
//                    public Observable<? extends Result> call(Gank gank) {
//                        Timber.i(new Gson().toJson(gank.results()));
//                        return mDatabaseHelper.setGanks(gank.results());
//                    }
//                });
//    }

//    public Flowable<Result> syncGank() {
//        return mGankService.getGanks()
//                .concatMap(new Function<Gank, Publisher<? extends Result>>() {
//                    @Override
//                    public Publisher<? extends Result> apply(@NonNull Gank gank) throws Exception {
//                        Timber.d(new Gson().toJson(gank.results()));
//                        return mDatabaseHelper.setGanks(gank.results());
//                    }
//                });
//    }

    public Flowable<Result> syncGank(int size, int page) {
        return mGankHttpService.getGankList(size, page)
                .concatMap(new Function<HttpListResult<Result>, Publisher<? extends Result>>() {
                    @Override
                    public Publisher<? extends Result> apply(@NonNull HttpListResult<Result> resultHttpListResult) throws Exception {
                        Timber.d("get result from internet: " + new Gson().toJson(resultHttpListResult.results));
                        return mDatabaseHelper.setGanks(resultHttpListResult.results);
                    }
                });
    }

    public Flowable<List<Result>> getGanks() {
        return mDatabaseHelper.getGanks().distinct();
    }
}
