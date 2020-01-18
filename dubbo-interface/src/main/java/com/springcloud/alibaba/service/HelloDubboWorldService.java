package com.springcloud.alibaba.service;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/31
 * Time: 9:19 AM
 * Description: No Description
 */
public interface HelloDubboWorldService {

    String sayHello(String name);

    Result<String> testGeneric(Person person);
}
