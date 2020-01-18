package com.springcloud.alibaba;

import com.springcloud.alibaba.xuzf.provider.service.impl.TestGenericService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/26
 * Time: 8:31 AM
 * Description: No Description
 */
public class TestGeneric {
    public static void main( String[] args ) {

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("providerApp");

// 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("localhost:9090");
        registry.setUsername("xuzf");
        registry.setPassword("xuzf12121");

// 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(18080);
        protocol.setThreads(20);
        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口实现
        GenericService testGenericService = new TestGenericService();

// 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setApplication(application);
// 弱类型接口名
        service.setInterface(GenericService.class);
        service.setVersion("1.0.0");
// 指向一个通用服务实现
        service.setRef(testGenericService);

// 暴露及注册服务
        service.export();
    }
}
