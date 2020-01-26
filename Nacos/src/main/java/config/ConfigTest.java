package config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.AbstractHealthChecker;
import com.alibaba.nacos.api.naming.pojo.Cluster;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.Service;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/19
 * Time: 8:24 PM
 * Description: No Description
 *
 */
public class ConfigTest {
    public static final  String serverAddr="127.0.0.1:8848";
    public static void main( String[] args ) throws NacosException {
        ConfigTest configTest = new ConfigTest();
        String dataId ="nacos-dev.properties";
        String group ="DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        getConfig(configService,dataId,group);
//        publishConfig(configService,dataId,group,"aa=bb");
       // System.out.println(getConfig(configService,dataId,group));
        configTest.removeConfig(configService,dataId,group);
        Listener listener = new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve1:" + configInfo);
            }
            @Override
            public Executor getExecutor() {
                return null;
            }
        };

        configTest.registerInstance();

        // 测试让主线程不退出，因为订阅配置是守护线程，主线程退出守护线程就会退出。 正式代码中无需下面代码
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getConfig(ConfigService configService,String dataId,String group) throws NacosException {
        String config = configService.getConfig(dataId,group,3000);
        return config;
    }

    /***
     * 添加一个监听器
     * @param dataId
     * @param group
     * @param listener
     */
    public void addListener(ConfigService configService,String dataId, String group, Listener listener) throws NacosException {
        configService.addListener(dataId,group,listener);
    }

    /***
     * 移除一个监听器
     * @param configService
     * @param dataId
     * @param group
     * @param listener
     */
    public void  removeListener(ConfigService configService,String dataId, String group, Listener listener){
        configService.removeListener(dataId,group,listener);
    }

    /***
     * 发布配置
     * @param configService
     * @param dataId
     * @param group
     * @param content
     * @throws NacosException
     */
    public static boolean publishConfig(ConfigService configService,String dataId,String group,String content) throws NacosException {
        return configService.publishConfig(dataId,group,content);
    }

    public boolean removeConfig(ConfigService configService,String dataId,String group) throws NacosException {
        return configService.removeConfig(dataId,group);
    }


    public void registerInstance() throws NacosException {
        NamingService naming = NamingFactory.createNamingService(serverAddr);
        naming.registerInstance("nacos.test.3", "11.11.11.11", 8888);
        //实例信息
        Instance instance = new Instance();
        instance.setIp("55.55.55.55");
        instance.setPort(9999);
        instance.setHealthy(false);
        instance.setWeight(2.0);
        Map<String, String> instanceMeta = new HashMap<>();
        instanceMeta.put("site", "et2");
        instance.setMetadata(instanceMeta);
        //服务信息
        Service service = new Service("nacos.test.4");
        service.setApp("nacos-naming");
        service.setHealthCheckMode("server");
        service.setProtectThreshold(0.8F);
        service.setGroup("CNCF");
        Map<String, String> serviceMeta = new HashMap<>();
        serviceMeta.put("symmetricCall", "true");
        service.setMetadata(serviceMeta);
        //实例绑定服务
        instance.setService(service);

        //集群信息
        Cluster cluster = new Cluster();
        cluster.setName("TEST5");
        AbstractHealthChecker.Http healthChecker = new AbstractHealthChecker.Http();
        healthChecker.setExpectedResponseCode(400);
        healthChecker.setPath("/xxx.html");
        cluster.setHealthChecker(healthChecker);
        Map<String, String> clusterMeta = new HashMap<>();
        clusterMeta.put("xxx", "yyyy");
        cluster.setMetadata(clusterMeta);
        //实例绑定集群
        instance.setCluster(cluster);
        //注册服务
        naming.registerInstance("nacos.test.4", instance);

    }

    /***
     * 注销服务实例
     * @throws NacosException
     */
    public void deregisterInstance() throws NacosException {
        String serverAddr ="";
        NamingService naming = NamingFactory.createNamingService(serverAddr);
        naming.deregisterInstance("nacos.test.3","11.11.11.11", 8888,"TEST1");

    }

    public List<Instance> getAllInstances( String serviceName) throws NacosException {
        String serverAddr ="";
        NamingService naming = NamingFactory.createNamingService(serverAddr);

        return naming.getAllInstances(serviceName);
    }

    /***
     * 根据健康状态获取实例
     * @param serviceName
     * @param health
     * @return
     * @throws NacosException
     */
    public List<Instance> selectInstances( String serviceName, boolean health) throws NacosException {
        String serverAddr ="";
        NamingService naming = NamingFactory.createNamingService(serverAddr);

        return naming.selectInstances(serviceName,health);
    }

    /***
     * 根据负载均衡算法随机获取一个健康实例。
     * @param serviceName
     * @return
     * @throws NacosException
     */
    public Instance selectOneHealthyInstance( String serviceName) throws NacosException {
        String serverAddr ="";
        NamingService naming = NamingFactory.createNamingService(serverAddr);

        return naming.selectOneHealthyInstance(serviceName);
    }

    /**
     * 监听服务下的实例列表变化。
     * @param serviceName
     * @param listener
     */
    public void subscribe(String serviceName, EventListener listener) throws NacosException {
        NamingService naming = NamingFactory.createNamingService(System.getProperty("serveAddr"));
        naming.subscribe("nacos.test.3", event -> {
            if (event instanceof NamingEvent) {
                System.out.println(((NamingEvent) event).getServiceName());
                System.out.println(((NamingEvent) event).getInstances());
            }
        });
    }

    /**
     * 取消监听服务下的实例列表变化
     * @param serviceName
     * @param listener
     * @throws NacosException
     */
    public void unsubscribe(String serviceName, EventListener listener) throws NacosException {
        NamingService naming = NamingFactory.createNamingService(System.getProperty("serveAddr"));
        naming.unsubscribe("nacos.test.3", event -> {});
    }
}
