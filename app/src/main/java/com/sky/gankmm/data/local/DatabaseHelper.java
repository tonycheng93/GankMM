package com.sky.gankmm.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sky.gankmm.data.model.Result;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public Observable<Result> setGanks(final List<Result> results) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.GankTable.TABLE_NAME, null);
                    for (Result result : results) {
                        long count = mDb.insert(Db.GankTable.TABLE_NAME, Db.GankTable.toContentValues(results),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (count >= 0) subscriber.onNext(result);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Result>> getGanks() {
        return mDb.createQuery(Db.GankTable.TABLE_NAME,
                "SELECT * FROM " + Db.GankTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Result>() {
                    @Override
                    public Result call(Cursor cursor) {
                        return Db.GankTable.parseCursor(cursor);
                    }
                });

    }
}
