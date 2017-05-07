package com.sky.gankmm.ui.main;

import com.google.gson.Gson;

import com.sky.gankmm.data.DataManager;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.ui.base.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by tonycheng on 2017/2/14.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

//    public void loadGanks() {
//        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
//        mSubscription = mDataManager.getGanks()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<List<Result>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e(e, "There was an error loading the ganks.");
//                        getMvpView().showError();
//                    }
//
//                    @Override
//                    public void onNext(List<Result> results) {
//                        if (results.isEmpty()) {
//                            getMvpView().showGanksEmpty();
//                        } else {
//                            getMvpView().showGanks(results);
//                        }
//                    }
//                });
//    }

    public void loadGanks() {
        Timber.d("loadGanks().");
        checkViewAttached();
        mDataManager.getGanks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<Result>>() {
                    @Override
                    public void onSubscribe(@NonNull org.reactivestreams.Subscription s) {
                        Timber.d("onSubscribe().");
                        s.request(10);
                        Timber.d("onSubscribe() current thread: " + Thread.currentThread().getName());
                        getMvpView().showLoading();
                    }

                    @Override
                    public void onNext(List<Result> results) {
                        Timber.d("onNext: " + new Gson().toJson(results));
                        if (results.isEmpty()) {
                            getMvpView().hideLoading();
                            getMvpView().showGanksEmpty();
                        } else {
                            getMvpView().hideLoading();
                            getMvpView().showGanks(results);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t, "There was an error loading the ganks.");
                        getMvpView().hideLoading();
                        getMvpView().showError();
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete().");
                    }
                });
    }

    public void loadGanks(int size, int page) {
        checkViewAttached();
        mDataManager.getGanks(size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<Result>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Timber.d("onSubscribe().");
                        s.request(10);
                        Timber.d("onSubscribe() current thread: " + Thread.currentThread().getName());
                        getMvpView().showLoading();
                    }

                    @Override
                    public void onNext(List<Result> results) {
                        Timber.d("onNext: " + new Gson().toJson(results));
                        if (results.isEmpty()) {
                            getMvpView().hideLoading();
                            getMvpView().showGanksEmpty();
                        } else {
                            getMvpView().hideLoading();
                            Timber.d("results hashcode: " + results.hashCode());
                            getMvpView().loadMoreGanks(results);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t, "There was an error loading the ganks.");
                        getMvpView().hideLoading();
                        getMvpView().showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
