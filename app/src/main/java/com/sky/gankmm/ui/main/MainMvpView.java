package com.sky.gankmm.ui.main;

import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.ui.base.MvpView;

import java.util.List;

/**
 * Created by tonycheng on 2017/2/14.
 */

public interface MainMvpView extends MvpView {

    void showLoading();

    void showGanks(List<Result> results);

    void loadMoreGanks(List<Result> results);

    void showGanksEmpty();

    void showError();

    void hideLoading();
}
