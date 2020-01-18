package sentinel.util;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 9:42 AM
 * Description: No Description
 */
public class FlowRuleUtil {

    static {
        //初始化限流规则
        initFlowRule();
    }

    public static void initFlowRule(){
        FlowRule rule = new FlowRule();
        rule.setResource("createOrder");
        rule.setLimitApp("orderApp");
        rule.setCount(2);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rule.setStrategy(RuleConstant.STRATEGY_DIRECT);

    }


    /***
     * 定义熔断降级规则
     */
    private static void initDegradeRule(){
        List<DegradeRule> rules = new ArrayList <>();
        DegradeRule rule = new DegradeRule();

        rule.setResource("createOrder_degradeRUle");//资源名，即限流规则的作用对象
        rule.setCount(20);//阈值

        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);//降级模式，根据 RT 降级还是根据异常比例降级，默认RT
        rule.setTimeWindow(10);//降级的时间，单位为 s
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);

    }

    /***
     * 系统保护规则
     */
    private static void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);//最大的 load，参考值 默认-1不生效
        rule.setAvgRt(-1);//所有入口流量的平均响应时间 默认-1不生效
        rule.setMaxThread(-1);//入口流量的最大并发数，默认-1不生效
        rule.setQps(-1);//所有入口资源的 QPS	 默认-1不生效
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }

    /**
     * 定义黑名单规则
     */
    private static void initBlackRules(){
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("createOrder_blackRule");
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("xuzf1");
        //One resource should only have at most one authority rule,
        //所以rule2会被忽略
        AuthorityRule rule2 = new AuthorityRule();
        rule2.setResource("createOrder_blackRule");
        rule2.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule2.setLimitApp("xuzf2");
        List<AuthorityRule> rules = new ArrayList<>();
        rules.add(rule);
        rules.add(rule2);
        AuthorityRuleManager.loadRules(rules);
    }

    /**
     * 定义白名单规则
     */
    private static void initWhiteRules(){
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("createOrder_whiteRule");
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appA,appE");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

}
