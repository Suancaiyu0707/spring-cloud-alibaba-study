//package sentinel.gateway;
//
//import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
//import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
//import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created with IntelliJ IDEA.
// * User: zhifang.xu
// * Date: 2019/11/23
// * Time: 9:12 PM
// * Description: No Description
// * 若使用的是 Spring Cloud Netflix Zuul，我们可以直接在配置类中将三个 filter 注入到 Spring 环境中即可：
// */
//@Configuration
//public class ZuulConfig {
//
//    @Bean
//    public ZuulFilter sentinelZuulPreFilter() {
//        // We can also provider the filter order in the constructor.
//        return new SentinelZuulPreFilter();
//    }
//
//    @Bean
//    public ZuulFilter sentinelZuulPostFilter() {
//        return new SentinelZuulPostFilter();
//    }
//
//    @Bean
//    public ZuulFilter sentinelZuulErrorFilter() {
//        return new SentinelZuulErrorFilter();
//    }
//}