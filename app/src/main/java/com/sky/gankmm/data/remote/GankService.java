package com.sky.gankmm.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.sky.gankmm.data.model.Gank;
import com.sky.gankmm.util.AutoValueGsonFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by tonycheng on 2017/2/14.
 */

public interface GankService {

    String ENDPOINT = "http://gank.io/api/";

    @GET("data/Android/10/1")
    Observable<Gank> getGanks();

    /********
     * Helper class that sets up a new services
     *******/
    class Creator {
        public static GankService newGankService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(AutoValueGsonFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit.create(GankService.class);
        }
    }
}
