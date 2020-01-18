package com.springcloud.alibaba.xuzf.provider.service.impl;

import com.springcloud.alibaba.service.HelloProviderService;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/12
 * Time: 7:29 AM
 * Description: No Description
 */
@Service
public class HelloProviderServiceImpl implements HelloProviderService {
    public String sayHello(String name) {
        String index = RpcContext.getContext().getAttachment("index");
        if(StringUtils.isNotEmpty(index)){
            System.out.println("隐式参数index: "+index);
        }
        return "Hello " + name;
    }
}
