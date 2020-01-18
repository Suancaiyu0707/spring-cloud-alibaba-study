package com.springcloud.alibaba.service;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/27
 * Time: 8:01 AM
 * Description: No Description
 */
public interface CallbackService {
    void addListener(String key, CallbackListener listener);
}
