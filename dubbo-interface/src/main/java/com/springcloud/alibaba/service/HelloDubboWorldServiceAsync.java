package com.springcloud.alibaba.service;

import java.util.concurrent.CompletableFuture;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/31
 * Time: 9:23 AM
 * Description: No Description
 * 异步调用接口
 */
public interface HelloDubboWorldServiceAsync {
    CompletableFuture<String> sayHello( String name);
}
