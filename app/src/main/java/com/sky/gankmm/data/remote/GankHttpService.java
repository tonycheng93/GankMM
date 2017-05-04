package com.sky.gankmm.data.remote;


import com.sky.gankmm.data.model.Result;
import com.sky.gankmm.http.core.HttpListResult;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tonycheng on 2017/4/25.
 */

interface GankHttpService {

    @GET("data/Android/{size}/{page}")
    Flowable<HttpListResult<Result>> getGankList(@Path("size") int size, @Path("page") int page);
}
