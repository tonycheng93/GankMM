package com.sky.gankmm.ui.main;

import com.sky.gankmm.data.DataManager;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.ui.base.BasePresenter;
import com.sky.gankmm.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;
import timber.log.Timber;


/**
 * Created by tonycheng on 2017/2/14.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

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
        if (mSubscription != null) mSubscription.unsubscribe();
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
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mDataManager.getGanks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<Result>>() {
                    @Override
                    public void onSubscribe(@NonNull org.reactivestreams.Subscription s) {
                        mSubscription = (Subscription) s;
                    }

                    @Override
                    public void onNext(List<Result> results) {
                        if (results.isEmpty()) {
                            getMvpView().showGanksEmpty();
                        } else {
                            getMvpView().showGanks(results);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t, "There was an error loading the ganks.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
