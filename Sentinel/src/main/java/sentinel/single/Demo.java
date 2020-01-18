package sentinel.single;
import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/10/18
 * Time: 11:08 PM
 * Description: No Description
 */
public class Demo {

    private static final String RESOURCE_NAME = "HelloWorld";
    public static void main(String[] args) throws BlockException {
        test1();
    }

    /***
     * 定义流量控制规则
     * 通过调用 FlowRuleManager.loadRules() 方法来用硬编码的方式定义流量控制规则，
     */
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");//资源名，资源名是限流规则的作用对象
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//限流阈值类型，QPS 或线程数模式
        // Set limit QPS to 20. 预先设定的阈值
        rule.setCount(20);//限流阈值 每秒通过20次请求
        //流控针对的调用来源 ,默认是default代表不区分调用来源
        rule.setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
        //流控效果（直接拒绝 / 排队等待 / 慢启动模式）,默认是default直接拒绝
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /***
     * 定义熔断降级规则
     */
    private static void initDegradeRule(){
        List<DegradeRule> rules = new ArrayList <>();
        DegradeRule rule = new DegradeRule();

        rule.setResource("HelloWorld");//资源名，即限流规则的作用对象
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
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("appA,appB");
        //One resource should only have at most one authority rule,
        //所以rule2会被忽略
        AuthorityRule rule2 = new AuthorityRule();
        rule2.setResource(RESOURCE_NAME);
        rule2.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule2.setLimitApp("appA");
        List<AuthorityRule> rules = new ArrayList <>();
        rules.add(rule);
        rules.add(rule2);
        AuthorityRuleManager.loadRules(rules);
    }

    /**
     * 定义白名单规则
     */
    private static void initWhiteRules(){
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appA,appE");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

    private static void test1(){
        // 配置规则.
        initFlowRules();

        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            //资源名可使用任意有业务语义的字符串，比如方法名、接口名或其它可唯一标识的字符串。
            ContextUtil.enter("HelloWorld-contextName");
            Entry entry =null;
            try {
                entry = SphU.entry("HelloWorld");
                //将 System.out.println("hello world"); 作为资源（被保护的逻辑）
//                Entry entry = SphU.entry("HelloWorld");
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                //// 资源访问阻止，被限流或被降级
                //  // 在此处进行相应的处理操作

                System.out.println("blocked!");
            }finally {
                if (entry != null) {
                    entry.exit();
                }
            }
            ContextUtil.exit();
        }
    }

    /***
     * 另外通过 Tracer.trace(ex) 来统计异常信息时，由于 try-with-resources 语法中 catch 调用顺序的问题，会导致无法正确统计异常数，
     * 因此统计异常信息时也不能在 try-with-resources 的 catch 块中调用 Tracer.trace(ex)。
     */
    public static void test2(){
        //(1) 配置规则.
        initFlowRules();
        //(2)定义资源
        Entry entry = null;
        int count =10;
        try {
            // 资源名可使用任意有业务语义的字符串
            //若 entry 的时候传入了热点参数，那么 exit 的时候也一定要带上对应的参数（exit(count, args)），否则可能会有统计错误。
            // 这个时候不能使用 try-with-resources 的方式。
            entry = SphU.entry("资源名：test2",count);
            // 被保护的业务逻辑
            // do something...
            System.out.println("hello world test2");
        } catch (BlockException e1) {
            // 资源访问阻止，被限流或被降级
            // 进行相应的处理操作
        } finally {// 务必保证finally会被执行
            if (entry != null) {
//                System.out.println(entry);
                entry.exit(count);
            }
        }
    }

    /***
     * SphO 提供 if-else 风格的 API。用这种方式，当资源发生了限流之后会返回 false，这个时候可以根据返回值，进行限流之后的逻辑处理。
     */
    public static void test3(){
        // 资源名可使用任意有业务语义的字符串
        String sorcename = "资源名:SphO:test3";
        if(SphO.entry(sorcename)){
            // 务必保证finally会被执行
            try {
                /**
                 * 被保护的业务逻辑
                 */
                System.out.println("获得资源: "+sorcename);
            } finally {
                SphO.exit();
            }
        }else{
            // 资源访问阻止，被限流或被降级
            // 进行相应的处理操作
            System.out.println("未获得资源: "+sorcename);
        }
    }

    /**
     * Sentinel 支持异步调用链路的统计。在异步调用中，需要通过 SphU.asyncEntry(xxx) 方法定义资源，并通常需要在异步的回调函数中调用 exit 方法
     * SphU.asyncEntry(xxx) 不会影响当前（调用线程）的 Context，因此以下两个 entry 在调用链上是平级关系（处于同一层）
     *      asyncEntry = SphU.asyncEntry(asyncResource);
     *      entry = SphU.entry(normalResource);
     */
    public static void test4(){
        String sorcename = "资源名:SphU.asyncEntry:test4";
        try {
            AsyncEntry entry = SphU.asyncEntry(sorcename);

            // 异步调用.
//            doAsync(userId, result -> {
//                try {
//                    // 在此处处理异步调用的结果.
//                } finally {
//                    // 在回调结束后 exit.
//                    entry.exit();
//                }
//            });
        } catch (BlockException ex) {
            // Request blocked.
            // Handle the exception (e.g. retry or fallback).
        }
    }

    private static void test6() throws BlockException {
        initBlackRules();
        testFor(RESOURCE_NAME, "appA");
        testFor(RESOURCE_NAME, "appB");
        testFor(RESOURCE_NAME, "appC");
        testFor(RESOURCE_NAME, "appE");

        System.out.println("========Testing for white list========");
        initWhiteRules();
        testFor(RESOURCE_NAME, "appA");
        testFor(RESOURCE_NAME, "appB");
        testFor(RESOURCE_NAME, "appC");
        testFor(RESOURCE_NAME, "appE");
    }
    private static void testFor(/*@NonNull*/ String resource, /*@NonNull*/ String origin) throws BlockException {
        //The origin is useful when we want to control different invoker/consumer separately.
        //orign用来控制区分不同的消费者或者代理，这里的orgin对应limitApp
        Context context=ContextUtil.enter(resource, origin);
        Entry entry = null;
        try {
            entry = SphU.entry(resource);
            System.out.println(String.format("Passed for resource %s, origin is %s", resource, origin));
        } catch (BlockException ex) {
            System.err.println(String.format("Blocked for resource %s, origin is %s", resource, origin));
            //throw ex;

        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
    }


}
