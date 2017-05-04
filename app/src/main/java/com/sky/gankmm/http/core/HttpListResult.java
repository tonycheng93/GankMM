package com.sky.gankmm.http.core;

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
public class HttpListResult<T> {
    public boolean error = false;
    public List<T> results;
}
