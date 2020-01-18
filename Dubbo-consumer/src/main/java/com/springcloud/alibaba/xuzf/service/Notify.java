package com.springcloud.alibaba.xuzf.service;

import com.springcloud.alibaba.service.Person;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/27
 * Time: 8:13 AM
 * Description: No Description
 */
public interface Notify {
    public void onreturn( Person msg, Integer id);
    public void onthrow(Throwable ex, Integer id);
}
