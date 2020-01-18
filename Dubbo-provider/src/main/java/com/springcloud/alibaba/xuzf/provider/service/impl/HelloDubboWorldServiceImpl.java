package com.springcloud.alibaba.xuzf.provider.service.impl;

import com.springcloud.alibaba.service.HelloDubboWorldService;
import com.springcloud.alibaba.service.Person;
import com.springcloud.alibaba.service.Result;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.RpcContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/12/31
 * Time: 9:27 AM
 * Description: No Description
 */
public class HelloDubboWorldServiceImpl  implements HelloDubboWorldService {
    @Override
    public String sayHello( String name ) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException ie){

        }
        return "Hello "+name +", from "+RpcContext.getContext().getAttachment("company");
    }

    @Override
    public Result<String> testGeneric( Person person ) {
        Result<String> result = new Result <>();
        result.setCode(1);

        try {
            result.setData(JSON.json(person));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
