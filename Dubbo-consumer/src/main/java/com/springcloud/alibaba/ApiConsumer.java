package com.springcloud.alibaba;

import com.springcloud.alibaba.service.HelloDubboWorldService;
import com.springcloud.alibaba.service.HelloProviderService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/31
 * Time: 9:36 AM
 * Description: No Description
 */
public class ApiConsumer {

    public static void main( String[] args ) {
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<HelloDubboWorldService> referenceConfig = new ReferenceConfig<HelloDubboWorldService>();

        referenceConfig.setApplication(new ApplicationConfig("hello-dubbo-world-consumer"));

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        referenceConfig.setRegistry(registry); // 多个注册中心可以用setRegistries()
        // 弱类型接口名
        referenceConfig.setInterface(HelloDubboWorldService.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setTimeout(5000);
        referenceConfig.setGroup("dubbo");

        HelloDubboWorldService helloDubboWorldService =referenceConfig.get();
        RpcContext.getContext().setAttachment("company","hellobike");

        System.out.println(helloDubboWorldService.sayHello("xuzf"));
    }
}
