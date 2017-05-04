package com.sky.gankmm.http.core;

import io.reactivex.subscribers.DefaultSubscriber;

/**
 * 项目名称：GankMM
 * 类描述：
 * 创建人：tonycheng
 * 创建时间：2017/5/4 23:32
 * 邮箱：tonycheng93@outlook.com
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public abstract class NoCompleteSubscriber<T> extends DefaultSubscriber<T> {
    @Override
    public void onComplete() {
    }
}
