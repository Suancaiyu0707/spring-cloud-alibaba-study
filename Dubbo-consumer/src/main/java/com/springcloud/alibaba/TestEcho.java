package com.springcloud.alibaba;

import com.springcloud.alibaba.service.HelloProviderService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.EchoService;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/26
 * Time: 8:40 AM
 * Description: No Description
 */
public class TestEcho {
    public static void main( String[] args ) {
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<HelloProviderService> reference = new ReferenceConfig<HelloProviderService>();

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("localhost:2181");
        registry.setUsername("xuzf");
        registry.setPassword("xuzf12121");

        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        // 弱类型接口名
        reference.setInterface(HelloProviderService.class);
        reference.setVersion("1.0.0");

        EchoService echoService = (EchoService) reference.get();
        // 回声测试可用性
        String status = (String) echoService.$echo("OK");

        assert(status.equals("OK"));

    }
}
