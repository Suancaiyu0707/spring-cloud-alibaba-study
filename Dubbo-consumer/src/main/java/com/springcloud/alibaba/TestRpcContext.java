package com.springcloud.alibaba;

import com.springcloud.alibaba.service.HelloProviderService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.EchoService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/26
 * Time: 8:40 AM
 * Description: No Description
 */
public class TestRpcContext {
    public static void main( String[] args ) {
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<HelloProviderService> reference = new ReferenceConfig<HelloProviderService>();

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("localhost:2181");

        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        // 弱类型接口名
        reference.setInterface(HelloProviderService.class);
        reference.setVersion("1.0.0");

        boolean isConsumer = RpcContext.getContext().isConsumerSide();
        // 获取最后一次调用的提供方IP地址
        String serverIP = RpcContext.getContext().getRemoteHost();
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        String application = RpcContext.getContext().getUrl().getParameter("application");
        // 注意：每发起RPC调用，上下文状态会变化
        HelloProviderService helloProviderService =reference.get();
        helloProviderService.sayHello("xuzf");

    }
}
