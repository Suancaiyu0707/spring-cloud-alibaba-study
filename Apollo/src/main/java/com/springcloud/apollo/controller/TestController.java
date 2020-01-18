package com.springcloud.apollo.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.springcloud.apollo.componment.DubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/13
 * Time: 9:55 PM
 * Description: No Description
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DubboConfig dubboConfig;
    @ApolloConfig
    private Config config;
    @RequestMapping("/test")
    public DubboConfig test(){
        System.out.println(dubboConfig);
        System.out.println(config);

        String someKey = "dubbo.address";
        String someDefaultValue = "localhost";
        String value = config.getProperty(someKey, someDefaultValue);

        System.out.println("value="+value);
        return dubboConfig;

    }
    @ApolloConfigChangeListener
    public void testAddListener(ConfigChangeEvent changeEvent){
        System.out.println("=======ApolloConfigChangeListener======");
    }

}
