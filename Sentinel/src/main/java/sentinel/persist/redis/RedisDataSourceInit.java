//package sentinel.persist.redis;
//
//import com.alibaba.csp.sentinel.datasource.Converter;
//import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
//import com.alibaba.csp.sentinel.init.InitFunc;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
//import com.alibaba.csp.sentinel.slots.system.SystemRule;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * User: zhifang.xu
// * Date: 2019/11/2
// * Time: 2:25 PM
// * Description: No Description
// */
//public class RedisDataSourceInit implements InitFunc {
//    @Override
//    public void init() throws Exception {
//        RedisConnectionConfig config = RedisConnectionConfig.builder()
//                .withHost(RedisConstant.host)
//                .withPort(RedisConstant.port)
//                .build();
////        ReadableDataSource<String,List<FlowRule>> redisSource = new RedisDataSource <>();
//
//    }
//    /**
//     * 流控规则对象转换
//     */
//    private Converter<String, List<FlowRule>> flowRuleListParser = source -> JSON.parseObject(
//            source,
//            new TypeReference<List<FlowRule>>() {
//            }
//    );
//    /**
//     * 降级规则对象转换
//     */
//    private Converter<String, List<DegradeRule>> degradeRuleListParser = source -> JSON.parseObject(
//            source,
//            new TypeReference<List<DegradeRule>>() {
//            }
//    );
//    /**
//     * 系统规则对象转换
//     */
//    private Converter<String, List<SystemRule>> systemRuleListParser = source -> JSON.parseObject(
//            source,
//            new TypeReference<List<SystemRule>>() {
//            }
//    );
//
//    /**
//     * 授权规则对象转换
//     */
//    private Converter<String, List<AuthorityRule>> authorityRuleListParser = source -> JSON.parseObject(
//            source,
//            new TypeReference<List<AuthorityRule>>() {
//            }
//    );
//
//    /**
//     * 热点规则对象转换
//     */
//    private Converter<String, List<ParamFlowRule>> hotParamFlowRuleListParser = source -> JSON.parseObject(
//            source,
//            new TypeReference<List<ParamFlowRule>>() {
//            }
//    );
//}
