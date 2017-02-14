package com.sky.gankmm.util;

import com.google.gson.TypeAdapterFactory;

import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;


/**
 * Created by tonycheng on 2017/2/13.
 */
@GsonTypeAdapterFactory
public abstract class AutoValueGsonFactory implements TypeAdapterFactory{

    public static TypeAdapterFactory create(){
        return new AutoValueGson_AutoValueGsonFactory();
    }
}
