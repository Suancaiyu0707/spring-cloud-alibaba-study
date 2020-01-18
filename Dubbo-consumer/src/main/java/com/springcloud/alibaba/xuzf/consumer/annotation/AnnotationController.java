package com.springcloud.alibaba.xuzf.consumer.annotation;

import com.springcloud.alibaba.service.*;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/12
 * Time: 8:53 AM
 * Description: No Description
 */
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @Reference
    private HelloAnnotationProviderService annotationService;
    @Reference
    private HelloProviderService providerService;

    @Reference(generic = true)
    private HelloProviderService genericService;
    @Reference(methods = {
       @Method(name="sayHello2",async = true)
    })
    private AsynHelloService asynHelloService;
    @Reference
    private CallbackService callbackService;
    @Reference(methods = {
            @Method(name = "get",async = true,
            onreturn = "nofifyImpl.onreturn",
            onthrow ="nofifyImpl.onthrow" )
    })
    private PersonService personService;
    @GetMapping("/hi")
    public String doSayHi(String name) {
        System.out.println("==============");
        String result = annotationService.sayHi(name);
        System.out.println(result);
        return result;
    }

    @GetMapping("/hi2")
    public String doSayHi2(String name) {
        System.out.println("==============");
        //隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用
        RpcContext.getContext().setAttachment("index", "1");
        String result = providerService.sayHello(name);
        System.out.println(result);
        return result;
    }

    /***
     * 泛化调用
     * @param name
     * @return
     */
    @GetMapping("/hi3")
    public Object doSayHi3(String name) {
        System.out.println("==============");
        GenericService genericService =(GenericService)providerService;
        Object result = genericService.$invoke("sayHello",new String[]{"java.lang.String"},new Object[]{"xuzf"});
        System.out.println(result);
        return result;
    }


    /***
     * 异步调用
     * @param name
     * @return
     */
    @GetMapping("/hi4")
    public void asyncSayHello(String name) {
        System.out.println("=======asyncSayHello=======");
        CompletableFuture<String> future =asynHelloService.sayHello(name);
        future.whenComplete((v,t)->{
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Response: " + v);
            }
        });
        // 早于结果输出
        System.out.println("Executed before response return.");
    }

    /***
     * 异步调用
     * @param name
     * @return
     */
    @GetMapping("/hi5")
    public void asyncSayHello2(String name) {
        System.out.println("=======asyncSayHello2=======");

        asynHelloService.sayHello2(name);
        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
        // 为Future添加回调
        helloFuture.whenComplete((retValue, exception) -> {
            if (exception == null) {
                System.out.println(retValue);
            } else {
                exception.printStackTrace();
            }
        });
    }



    /***
     * 参数回调
     * @param name
     * @return
     */
    @GetMapping("/hi6")
    public void callback(String name) {
        System.out.println("=======callback=======");
        callbackService.addListener("foo.bar", new CallbackListener(){
            public void changed(String msg) {
                System.out.println("callback1:" + msg);
            }
        });
    }


    /***
     * 事件通知
     * @param name
     * @return
     */
    @GetMapping("/hi6")
    public void notify(String name) {

    }
}
