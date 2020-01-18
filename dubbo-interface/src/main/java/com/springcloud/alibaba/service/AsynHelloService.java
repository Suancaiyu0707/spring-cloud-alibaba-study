package com.springcloud.alibaba.service;

import java.util.concurrent.CompletableFuture;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/27
 * Time: 7:32 AM
 * Description: No Description
 */
public interface AsynHelloService {
    CompletableFuture<String> sayHello(String name);

    String sayHello2(String name);
}
