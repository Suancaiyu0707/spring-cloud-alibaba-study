package com.springcloud.alibaba;

import com.springcloud.alibaba.service.HelloProviderService;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/26
 * Time: 8:26 AM
 * Description: No Description
 */
public class TestGenericService {
    public static void main( String[] args ) {
        testGeneric();
    }

    public static void testGeneric(){
        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("localhost:9090");
        registry.setUsername("xuzf");
        registry.setPassword("xuzf12121");

        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
// 弱类型接口名
        reference.setInterface(HelloProviderService.class);
        reference.setVersion("1.0.0");

// 声明为泛化接口
        reference.setGeneric(true);

// 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

// 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"world"});

    }
}
