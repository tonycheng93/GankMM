package com.sky.gankmm.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.gankmm.R;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.util.ImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by tonycheng on 2017/2/14.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.GankViewHolder> {

    private List<Result> mResults;

    @Inject
    public MainAdapter() {
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
        if (result == null) {
            return;
        }
        List<String> images = result.images();
        if (images != null) {
            holder.mBanner.setImages(images)
                    .setImageLoader(new ImageLoader())
                    .start();
        }
        String desc = result.desc();
        if (!desc.isEmpty()) {
            holder.mTitle.setText(desc);
        }
        String who = result.who();
        if (!who.isEmpty()) {
            holder.mAuthor.setText(who);
        }
        Date date = result.publishedAt();
        if (date != null) {
            holder.mTime.setText(date.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.img_banner)
        Banner mBanner;
//        @BindView(R.id.title_text_view)
        TextView mTitle;
//        @BindView(R.id.author_text_view)
        TextView mAuthor;
//        @BindView(R.id.time_text_view)
        TextView mTime;

        public GankViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            mBanner = (Banner) itemView.findViewById(R.id.img_banner);
            mTitle = (TextView) itemView.findViewById(R.id.title_text_view);
            mAuthor = (TextView) itemView.findViewById(R.id.author_text_view);
            mTime = (TextView) itemView.findViewById(R.id.time_text_view);
        }
    }
}
