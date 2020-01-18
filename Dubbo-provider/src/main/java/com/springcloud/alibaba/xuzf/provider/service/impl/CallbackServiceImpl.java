package com.springcloud.alibaba.xuzf.provider.service.impl;

import com.springcloud.alibaba.service.CallbackListener;
import com.springcloud.alibaba.service.CallbackService;
import org.apache.dubbo.config.annotation.Argument;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/27
 * Time: 8:02 AM
 * Description: No Description
 */
@Service(methods = {
        @Method(name="addListener",
                arguments = {
                    @Argument(
                            index = 1,
                            callback = true
                    )
                }
        )
})
public class CallbackServiceImpl implements CallbackService {
    private final Map<String, CallbackListener> listeners =
            new ConcurrentHashMap<String, CallbackListener>();

    public CallbackServiceImpl() {
        //启动一个线程不断的轮询监听器
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        for(Map.Entry<String, CallbackListener> entry : listeners.entrySet()){
                            try {
                                entry.getValue().changed(getChanged(entry.getKey()));
                            } catch (Throwable t) {
                                listeners.remove(entry.getKey());
                            }
                        }
                        Thread.sleep(5000); // 定时触发变更通知
                    } catch (Throwable t) { // 防御容错
                        t.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /***
     * 提供一个添加监听器的方法
     * @param key
     * @param listener
     */
    public void addListener(String key, CallbackListener listener) {
        listeners.put(key, listener);
        listener.changed(getChanged(key)); // 发送变更通知
    }

    private String getChanged(String key) {
        return "Changed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
