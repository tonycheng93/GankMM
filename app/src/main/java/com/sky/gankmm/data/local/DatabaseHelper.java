package com.sky.gankmm.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sky.gankmm.data.model.Result;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by tonycheng on 2017/2/14.
 */
@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite.Builder builder = new SqlBrite.Builder();
        mDb = builder.build().wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
    }

    public BriteDatabase getDb() {
        return mDb;
    }

//    public Observable<Result> setGanks(final Collection<Result> results) {
//        return Observable.create(new Observable.OnSubscribe<Result>() {
//            @Override
//            public void call(Subscriber<? super Result> subscriber) {
//                if (subscriber.isUnsubscribed()) return;
//                BriteDatabase.Transaction transaction = mDb.newTransaction();
//                try {
//                    mDb.delete(Db.GankTable.TABLE_NAME, null);
//                    for (Result result : results) {
//                        long count = mDb.insert(Db.GankTable.TABLE_NAME, Db.GankTable.toContentValues(result),
//                                SQLiteDatabase.CONFLICT_REPLACE);
//                        Timber.i("插入" + count + "条数据。");
//                        if (count >= 0) subscriber.onNext(result);
//                    }
//                    transaction.markSuccessful();
//                    subscriber.onCompleted();
//                } finally {
//                    transaction.end();
//                }
//            }
//        });
//    }

    public Flowable<Result> setGanks(final Collection<Result> results) {
        return Flowable.create(new FlowableOnSubscribe<Result>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Result> e) throws Exception {
                if (e.isCancelled()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.GankTable.TABLE_NAME, null);
                    for (Result result : results) {
                        long count = mDb.insert(Db.GankTable.TABLE_NAME, Db.GankTable.toContentValues(result),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Timber.d("insert " + count + " data.");
                        if (count >= 0) e.onNext(result);
                    }
                    transaction.markSuccessful();
                    e.onComplete();
                } finally {
                    transaction.end();
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

//    public Observable<List<Result>> getGanks() {
//        return mDb.createQuery(Db.GankTable.TABLE_NAME,
//                "SELECT * FROM " + Db.GankTable.TABLE_NAME)
//                .mapToList(new Func1<Cursor, Result>() {
//                    @Override
//                    public Result call(Cursor cursor) {
//                        return Db.GankTable.parseCursor(cursor);
//                    }
//                });
//
//    }

    public Flowable<List<Result>> getGanks() {
        return RxJavaInterop.toV2Flowable(mDb.createQuery(Db.GankTable.TABLE_NAME,
                "SELECT * FROM " + Db.GankTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Result>() {
                    @Override
                    public Result call(Cursor cursor) {
                        Timber.d("getGanks() from db.");
                        return Db.GankTable.parseCursor(cursor);
                    }
                }));
    }
}
