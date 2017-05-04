package com.sky.gankmm.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tonycheng on 2017/2/14.
 */
@AutoValue
public abstract class Result implements Parcelable {

    @SerializedName("_id")
    public abstract String id();

    public abstract String desc();

    @Nullable
    public abstract List<String> images();

    public abstract String publishedAt();

    public abstract String type();

    public abstract String url();

    @Nullable
    public abstract String who();

    public static Builder builder() {
        return new AutoValue_Result.Builder();
    }

    public static TypeAdapter<Result> typeAdapter(Gson gson) {
        return new AutoValue_Result.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(String id);

        public abstract Builder setDesc(String desc);

        public abstract Builder setImages(List<String> images);

        public abstract Builder setPublishedAt(String publishAt);

        public abstract Builder setType(String type);

        public abstract Builder setUrl(String url);

        public abstract Builder setWho(String who);

        public abstract Result build();
    }
}
