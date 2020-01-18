package com.springcloud.alibaba.xuzf;

import com.springcloud.alibaba.service.HelloDubboWorldService;
import com.springcloud.alibaba.xuzf.provider.service.impl.HelloDubboWorldServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/31
 * Time: 9:32 AM
 * Description: No Description
 */
public class ApiProvider {
    public static void main( String[] args ) throws IOException {
        ServiceConfig<HelloDubboWorldService> serviceConfig = new ServiceConfig <>();

        serviceConfig.setApplication(new ApplicationConfig("hello-dubbo-world-provider"));

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");

        serviceConfig.setRegistry(registryConfig);

        serviceConfig.setInterface(HelloDubboWorldService.class);

        serviceConfig.setRef(new HelloDubboWorldServiceImpl());

        serviceConfig.setVersion("1.0.0");

        serviceConfig.setGroup("dubbo");

        serviceConfig.export();

        System.out.println("Server is started");

        System.in.read();
    }
}
