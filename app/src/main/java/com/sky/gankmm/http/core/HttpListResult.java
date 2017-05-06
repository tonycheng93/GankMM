package com.sky.gankmm.http.core;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 项目名称：GankMM
 * 类描述：
 * 创建人：tonycheng
 * 创建时间：2017/5/4 23:28
 * 邮箱：tonycheng93@outlook.com
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@AutoValue
public abstract class HttpListResult<T> {
    public abstract boolean error();
    public abstract List<T> results();

    public static <T> HttpListResult<T> create(boolean error, List<T> results) {
        return HttpListResult.<T>builder()
                .error(error)
                .results(results)
                .build();
    }


    public static <T> Builder<T> builder() {
        return new AutoValue_HttpListResult.Builder<>();
    }

    @AutoValue.Builder
    public abstract static class Builder<T> {
        public abstract Builder<T> error(boolean error);

        public abstract Builder<T> results(List<T> results);

        public abstract HttpListResult<T> build();
    }

    public static <T> TypeAdapter<HttpListResult<T>> typeAdapter(Gson gson,
                                                                 TypeToken<? extends HttpListResult<T>> typeToken){
        return new AutoValue_HttpListResult.GsonTypeAdapter(gson,typeToken);
    }
}
