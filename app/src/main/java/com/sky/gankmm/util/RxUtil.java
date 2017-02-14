package com.sky.gankmm.util;

import rx.Subscription;

/**
 * Created by tonycheng on 2017/2/14.
 */

public class RxUtil {

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
