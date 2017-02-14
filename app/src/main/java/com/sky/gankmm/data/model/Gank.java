package com.sky.gankmm.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by tonycheng on 2017/2/13.
 */
@AutoValue
public abstract class Gank implements Parcelable{

    public abstract boolean error();

    public abstract List<Result> results();

    public static Gank create(boolean error, List<Result> results) {
        return new AutoValue_Gank(error,results);
    }

    public static TypeAdapter<Gank> typeAdapter(Gson gson){
        return new AutoValue_Gank.GsonTypeAdapter(gson);
    }
}
