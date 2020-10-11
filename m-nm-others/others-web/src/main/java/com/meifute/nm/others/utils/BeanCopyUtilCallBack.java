package com.meifute.nm.others.utils;

/**
 * @Classname BeanCopyUtilCallBack
 * @Description
 * @Date 2020-07-09 13:14
 * @Created by MR. Xb.Wu
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack<S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
