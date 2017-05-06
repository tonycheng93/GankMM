package com.sky.gankmm.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sky.gankmm.R;
import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.injection.ActivityContext;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tonycheng on 2017/4/26.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private static final int TYPE_PURE_TEXT = 1;
    private static final int TYPE_TEXT_WITH_IMAGE = 2;

    private Context mContext;
    private List<Result> mGankEntityList;
    private LayoutInflater mLayoutInflater;

    @Inject
    public MainAdapter(@ActivityContext Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setResults(List<Result> gankEntityList) {
        mGankEntityList = gankEntityList;
//        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PURE_TEXT) {
            View itemView = mLayoutInflater.inflate(R.layout.item_pure_text, parent, false);
            return new TextViewHolder(itemView);
        } else if (viewType == TYPE_TEXT_WITH_IMAGE) {
            View itemView = mLayoutInflater.inflate(R.layout.item_text_and_iamge, parent, false);
            return new ImageViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Result gankEntity = mGankEntityList.get(position);

        if (gankEntity == null) {
            return;
        }

        final int itemViewType = getItemViewType(position);

        if (itemViewType == TYPE_PURE_TEXT) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            final String desc = gankEntity.desc();
            if (!TextUtils.isEmpty(desc)) {
                textViewHolder.mTvDesc.setText(desc);
            }
            final String author = gankEntity.who();
            if (!TextUtils.isEmpty(author)) {
                textViewHolder.mTvAuthor.setText(String.format("via %s", author));
            } else {
                textViewHolder.mTvAuthor.setText("");
            }
            final String publishedAt = gankEntity.publishedAt();
            if (!TextUtils.isEmpty(publishedAt)) {
                String time = publishedAt.split("T")[0];
                textViewHolder.mTvTime.setText(time);
            }
        } else if (itemViewType == TYPE_TEXT_WITH_IMAGE) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            final List<String> images = gankEntity.images();
            Glide.with(mContext)
                    .load(images.get(0))
                    .asBitmap()
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageViewHolder.mIvImage);
            final String desc = gankEntity.desc();
            if (!TextUtils.isEmpty(desc)) {
                imageViewHolder.mTvDesc.setText(desc);
            }
            final String author = gankEntity.who();
            if (!TextUtils.isEmpty(author)) {
                imageViewHolder.mTvAuthor.setTextColor(Color.parseColor("#FFFFFF"));
                imageViewHolder.mTvAuthor.setText(String.format("via %s", author));
            } else {
                imageViewHolder.mTvAuthor.setText("");
            }
            final String publishedAt = gankEntity.publishedAt();
            if (!TextUtils.isEmpty(publishedAt)) {
                imageViewHolder.mTvTime.setTextColor(Color.parseColor("#FFFFFF"));
                String time = publishedAt.split("T")[0];
                imageViewHolder.mTvTime.setText(time);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        final Result gankEntity = mGankEntityList.get(position);
        if (gankEntity != null) {
            if (gankEntity.images() != null && gankEntity.images().size() > 0) {
                return TYPE_TEXT_WITH_IMAGE;
            } else {
                return TYPE_PURE_TEXT;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mGankEntityList == null ? 0 : mGankEntityList.size();
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {

        TextView mTvDesc;
        TextView mTvAuthor;
        TextView mTvTime;

        private TextViewHolder(View itemView) {
            super(itemView);
            mTvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mTvAuthor.setTextColor(Color.argb(87, 73, 73, 73));
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvTime.setTextColor(Color.argb(87, 73, 73, 73));
        }
    }

    private class ImageViewHolder extends TextViewHolder {

        private RelativeLayout mRelativeLayout;
        private ImageView mIvImage;

        ImageViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
            mRelativeLayout.setBackgroundColor(Color.argb(42, 0, 0, 0));
            mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
