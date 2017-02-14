package com.sky.gankmm.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.gankmm.R;
import com.sky.gankmm.data.model.Result;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonycheng on 2017/2/14.
 */

public class GanksAdapter extends RecyclerView.Adapter<GanksAdapter.GankViewHolder> {

    private List<Result> mResults;

    @Inject
    public GanksAdapter() {
        mResults = new ArrayList<>();
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_with_text_and_image, parent, false);
        return new GankViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {
        Result result = mResults.get(position);

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_banner)
        Banner mBanner;
        @BindView(R.id.title_text_view)
        TextView mTitle;
        @BindView(R.id.author_text_view)
        TextView mAuthor;
        @BindView(R.id.time_text_view)
        TextView mTime;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
