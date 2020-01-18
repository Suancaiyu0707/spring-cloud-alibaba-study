package com.springcloud.apollo.componment;

import com.springcloud.apollo.bean.TestApolloAnnotationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/13
 * Time: 10:46 PM
 * Description: No Description
 */
@Configuration
public class AppConfig {
    @Bean
    public DubboConfig javaConfigBean() {
        return new DubboConfig();
    }

    @Bean
    public TestApolloAnnotationBean testApolloAnnotationBean() {
        return new TestApolloAnnotationBean();
    }
}
