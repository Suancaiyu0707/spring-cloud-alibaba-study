package com.springcloud.alibaba;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/13
 * Time: 8:33 AM
 * Description: No Description
 */
@EnableDubbo(scanBasePackages = "com.springcloud.alibaba.xuzf.provider.service.impl")
//@PropertySource("classpath:/provider/provider.properties")
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
