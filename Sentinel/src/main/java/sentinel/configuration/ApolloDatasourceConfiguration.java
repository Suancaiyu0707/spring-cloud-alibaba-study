package sentinel.configuration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 11:41 AM
 * Description: No Description
 */
public class ApolloDatasourceConfiguration {

    private static void init(){

        String namespace ="flowRuleNamespace";

        String ruleKey = "flowRule";
        String defaultRules = null;
        //提供一个 Converter 来将数据S转换成最终的数据T
        Converter<String,List<FlowRule>> converter =source-> JSON.parseObject(source,new TypeReference <List<FlowRule>>(){});
        //实现readSource 方法，将数据源中的原始数据转换成我们可以处理的数据S
        ReadableDataSource<String,List<FlowRule>> apolloDatasource = new ApolloDataSource <>(namespace,ruleKey,
                defaultRules,converter);
        //将最终的数据T更新到具体的 RuleManager 中去（由SentinelProperty触发更新），所以最终更新规则的事情是PropertyListener去做的
        FlowRuleManager.register2Property(apolloDatasource.getProperty());
    }
}
