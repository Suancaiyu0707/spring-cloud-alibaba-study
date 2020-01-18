package sentinel.configuration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 11:46 AM
 * Description: No Description
 */
public class ZkDatasourceConfiguration {

    private static void init(){
        //zk的地址
        String remoteAddress ="localhost:2181";
        //zk下的存储路径
        String path = "/zk/flowrule/";
        //定义一个转换器，用于把存储源里的规则配置从String转换成List<FlowRule>
        Converter<String, List<FlowRule>> parser = source ->
                JSON.parseObject(source,new TypeReference<List<FlowRule>>() {});
        //创建一个 ZookeeperDataSource。里面定义了如何把数据从 zk里读取出来，读取完成之后会交给parser进行转换。
        //转换后的规则会存放到SentinelProperty里
        ReadableDataSource<String, List<FlowRule>> zookeeperDataSource =
                new ZookeeperDataSource<>(remoteAddress, path, parser);
        //将SentinelProperty注册到RuleManager中，所以最终更新规则的事情是PropertyListener去做的
        FlowRuleManager.register2Property(zookeeperDataSource.getProperty());
    }
}
