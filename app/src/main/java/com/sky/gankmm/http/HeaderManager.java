package com.sky.gankmm.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：GankMM
 * 类描述：
 * 创建人：tonycheng
 * 创建时间：2017/5/4 23:27
 * 邮箱：tonycheng93@outlook.com
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class HeaderManager {
    private final Map<String, String> mDefaultHeaders = new HashMap<>();

    private static class Holder {
        public static HeaderManager instance = new HeaderManager();
    }

    public static HeaderManager getInstance() {
        return Holder.instance;
    }
}
