package com.springcloud.alibaba.xuzf.provider.service.impl;

import com.springcloud.alibaba.service.AsynHelloService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/27
 * Time: 7:33 AM
 * Description: No Description
 */
@Service(async = true)
public class AsyncServiceImpl implements AsynHelloService {
    @Override
    public CompletableFuture<String> sayHello( String name ) {
        RpcContext context = RpcContext.getContext();
        //通过return CompletableFuture.supplyAsync()，业务执行已从Dubbo线程切换到业务线程，避免了对Dubbo线程池的阻塞。
        return CompletableFuture.supplyAsync(()->{
            System.out.println(context.getAttachment("consumer-key1"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "async response from provider.";
        });
    }

    @Override
    public String sayHello2( String name ) {
        final AsyncContext asyncContext = RpcContext.startAsync();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果要使用上下文，则必须要放在第一句执行
                asyncContext.signalContextSwitch();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 写回响应
                asyncContext.write("Hello " + name + ", response from provider.");
            }
        }).start();
        return null;
    }
}
