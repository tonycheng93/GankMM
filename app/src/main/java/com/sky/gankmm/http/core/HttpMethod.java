package com.sky.gankmm.http.core;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tonycheng on 2017/4/24.
 */

public abstract class HttpMethod<T> {

    private static final String TAG = "HttpMethod";

    private T mService = null;

    public abstract String getBaseUrl();//基地址

    public abstract int getTimeOut();//超时时间

    public abstract Class<T> getServiceClazz();

    //头信息
    public Map<String, String> getHeaders() {
        return null;
    }

    //支持自定义Gson
    public Gson getGson() {
        return null;
    }

    //支持自定义OkHttpClient
    public OkHttpClient getOkHttpClient() {
        return null;
    }

    protected HttpMethod() {
        Retrofit retrofit = null;
        if (getGson() == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient() == null ? getClient() : getOkHttpClient())
                    .baseUrl(getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient() == null ? getClient() : getOkHttpClient())
                    .baseUrl(getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }


        mService = retrofit.create(getServiceClazz());
    }

    private OkHttpClient getClient() {
        final Map<String, String> headers = getHeaders();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (headers != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request.Builder requestBuilder = chain.request().newBuilder();
                    for (Object o : headers.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        requestBuilder.addHeader((String) entry.getKey(), (String) entry.getValue
                                ());
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
            HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
            logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logginInterceptor);
        }
        builder.connectTimeout(getTimeOut(), TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    protected T getService() {
        return mService;
    }

    protected <E> Function<HttpResult<E>, E> map(Class<E> clazz) {
        return new Function<HttpResult<E>, E>() {
            @Override
            public E apply(@NonNull HttpResult<E> eHttpResult) throws Exception {
                if (eHttpResult != null) {
                    if (eHttpResult.results != null) {
                        return eHttpResult.results;
                    }
                }
                return null;
            }
        };
    }

    protected <E> Function<HttpListResult<E>, List<E>> mapList(Class<E> clazz) {
        return new Function<HttpListResult<E>, List<E>>() {
            @Override
            public List<E> apply(@NonNull HttpListResult<E> eHttpListResult) throws Exception {
                if (eHttpListResult != null) {
                    if (eHttpListResult.results != null) {
                        return eHttpListResult.results;
                    }
                }
                return null;
            }
        };
    }
}
