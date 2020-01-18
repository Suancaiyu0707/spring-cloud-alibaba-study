package sentinel.persist.redis;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/2
 * Time: 2:25 PM
 * Description: No Description
 */
public final class RedisConstant {
    public static final String host="127.0.0.1";
    public static final Integer port = 6379;

    //限流规则key前缀
    public final String RULE_FLOW = "sentinel_rule_flow_";
    public final String RULE_FLOW_CHANNEL = "sentinel_rule_flow_channel";
    //降级规则key前缀
    public final String RULE_DEGRADE = "sentinel_rule_degrade_";
    public final String RULE_DEGRADE_CHANNEL = "sentinel_rule_degrade_channel";
    //系统规则key前缀
    public final String RULE_SYSTEM = "sentinel_rule_system_";
    public final String RULE_SYSTEM_CHANNEL = "sentinel_rule_system_channel";

}
