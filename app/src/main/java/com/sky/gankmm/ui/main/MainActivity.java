package com.sky.gankmm.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sky.gankmm.R;
import com.sky.gankmm.data.SyncService;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.ui.base.BaseActivity;
import com.sky.gankmm.util.DialogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


public class MainActivity extends BaseActivity
        implements MainMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.sky.gankmm.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    MainAdapter mMainAdapter;

    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private List<Result> mResults = new ArrayList<>();
    private static final int SIZE = 10;
    private int mPage = 1;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
        mRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mMainAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(mOnScrollListener);
        mMainPresenter.attachView(this);
        mMainPresenter.loadGanks();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showLoading() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showGanks(List<Result> results) {
        mResults.addAll(results);
        mMainAdapter.setResults(mResults);
        mMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreGanks(List<Result> results) {
        if (results != null && results.size() > 0) {
            if (!mResults.containsAll(results)) {
                mResults.addAll(results);
                mMainAdapter.setResults(mResults);
                mMainAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showGanksEmpty() {
        mMainAdapter.setResults(Collections.<Result>emptyList());
        mMainAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_ganks, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ganks))
                .show();
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mMainPresenter.loadGanks(SIZE, mPage);
    }

    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == SCROLL_STATE_IDLE && lastVisibleItem == mMainAdapter.getItemCount() - 1) {
                //load more
                Timber.d("onScrollStateChanged load more.");
                mPage += 1;
                mMainPresenter.loadGanks(SIZE, mPage);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mRefreshLayout.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };
}
