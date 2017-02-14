package com.sky.gankmm.ui.base;

/**
 * Created by tonycheng on 2017/2/14.
 */

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
