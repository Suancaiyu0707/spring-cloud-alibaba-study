package com.springcloud.apollo.bean;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/14
 * Time: 7:52 PM
 * Description: No Description
 */
public class TestApolloAnnotationBean {
    @ApolloConfig
    private Config config; //inject config for namespace application
    @ApolloConfig("application")
    private Config anotherConfig; //inject config for namespace application
    @ApolloConfig("FX.apollo")
    private Config yetAnotherConfig; //inject config for namespace FX.apollo
    @ApolloConfig("application.yml")
    private Config ymlConfig; //inject config for namespace application.yml

    /**
     * ApolloJsonValue annotated on fields example, the default value is specified as empty list - []
     * <br />
     * jsonBeanProperty=[{"someString":"hello","someInt":100},{"someString":"world!","someInt":200}]
     */
    @ApolloJsonValue("${jsonBeanProperty:[]}")
    private List<JsonBean> anotherJsonBeans;

    @Value("${batch:100}")
    private int batch;

    //config change listener for namespace application
    @ApolloConfigChangeListener
    private void someOnChange(ConfigChangeEvent changeEvent) {
        //update injected value of batch if it is changed in Apollo
        if (changeEvent.isChanged("batch")) {
            batch = config.getIntProperty("batch", 100);
        }
    }

    //config change listener for namespace application
    @ApolloConfigChangeListener("application")
    private void anotherOnChange(ConfigChangeEvent changeEvent) {
        //do something
    }

    //config change listener for namespaces application, FX.apollo and application.yml
    @ApolloConfigChangeListener({"application", "FX.apollo", "application.yml"})
    private void yetAnotherOnChange(ConfigChangeEvent changeEvent) {
        //do something
    }

    //example of getting config from Apollo directly
    //this will always return the latest value of timeout
    public int getTimeout() {
        return config.getIntProperty("timeout", 200);
    }

    //example of getting config from injected value
    //the program needs to update the injected value when batch is changed in Apollo using @ApolloConfigChangeListener shown above
    public int getBatch() {
        return this.batch;
    }

    private static class JsonBean{
        private String someString;
        private int someInt;
    }
}
