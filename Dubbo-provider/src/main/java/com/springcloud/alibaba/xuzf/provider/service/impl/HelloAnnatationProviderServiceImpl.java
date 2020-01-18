package com.springcloud.alibaba.xuzf.provider.service.impl;

import com.springcloud.alibaba.service.HelloAnnotationProviderService;
import org.apache.dubbo.config.annotation.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/12
 * Time: 8:46 AM
 * Description: No Description
 */
@Service
public class HelloAnnatationProviderServiceImpl implements HelloAnnotationProviderService {
    public String sayHi( String name ) {
        System.out.println("name="+name);
        return "Hi! "+name;
    }
}
