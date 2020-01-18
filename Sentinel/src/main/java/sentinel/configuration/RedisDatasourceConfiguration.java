package sentinel.configuration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 11:29 AM
 * Description: No Description
 */
public class RedisDatasourceConfiguration {
    private void init() throws Exception {
        String redisHost ="localhost";//redis服务地址
        Integer redisPort = 6379;//redis端口号
        String ruleKey = "flowRule";//redis中的key
        String channel = "ruleChannel";//定义一个通道，用于监听rule的变化
        //定义一个转换器，用于将redisDataSource读取出来的字符串类型的规则进行转换成List<FlowRule>
        Converter<String, List<FlowRule>> parser = source ->
                JSON.parseObject(source,new TypeReference<List<FlowRule>>() {});
        RedisConnectionConfig config = RedisConnectionConfig.builder()
                .withHost(redisHost)
                .withPort(redisPort)
                .build();
        //实现readSource 方法，将数据源中的原始数据转换成我们可以处理的数据S
        ReadableDataSource<String, List<FlowRule>> redisDataSource =
                new RedisDataSource<>(config, ruleKey, channel, parser);
        //将最终的数据T更新到具体的 RuleManager 中去（由SentinelProperty触发更新），所以最终更新规则的事情是PropertyListener去做的
        FlowRuleManager.register2Property(redisDataSource.getProperty());
    }
}
