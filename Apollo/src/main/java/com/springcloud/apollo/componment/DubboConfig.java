package com.springcloud.apollo.componment;

import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/13
 * Time: 9:49 PM
 * Description: No Description
 */
@Component
@ConfigurationProperties(prefix = "dubbo")

public class DubboConfig {
    @Value("address")
    private String registryAddress;
    @Value("port")
    private String port;
    @Value("protocol")
    private String protocol;
//    @ApolloConfigChangeListener
    @Value("timeout")
    private String timeout;
    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress( String registryAddress ) {
        this.registryAddress = registryAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort( String port ) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol( String protocol ) {
        this.protocol = protocol;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout( String timeout ) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "DubboConfig{" +
                "registryAddress='" + registryAddress + '\'' +
                ", port='" + port + '\'' +
                ", protocol='" + protocol + '\'' +
                ", timeout='" + timeout + '\'' +
                '}';
    }
}
