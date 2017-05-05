package com.sky.gankmm.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sky.gankmm.GankApplication;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.util.AndroidComponentUtil;
import com.sky.gankmm.util.NetworkUtil;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by tonycheng on 2017/2/15.
 */

public class SyncService extends Service {

    @Inject
    DataManager mDataManager;
//    private Subscription mSubscription;

    private static final int SIZE = 10;
    private static final int PAGE = 1;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GankApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_STICKY;
        }
//        RxUtil.unsubscribe(mSubscription);
//        mSubscription = mDataManager.syncGank()
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Result>() {
//                    @Override
//                    public void onCompleted() {
//                        Timber.i("Synced successfully!");
//                        stopSelf(startId);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.w(e, "Error syncing.");
//                        stopSelf(startId);
//                    }
//
//                    @Override
//                    public void onNext(Result result) {
//
//                    }
//                });
        mDataManager.syncGank(SIZE, PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe(new FlowableSubscriber<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {

                    }

                    @Override
                    public void onNext(Result result) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.w(t, "Error syncing.");
                        stopSelf(startId);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Synced successfully!");
                        stopSelf(startId);
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
//        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }
}
