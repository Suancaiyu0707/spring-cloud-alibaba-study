package sentinel.configuration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 11:54 AM
 * Description: No Description
 */
public class NacosDatasourceConfiguration {
    private void init() {
        String remoteAddress = "";
        String groupId = "";
        String dataId = "";
        Converter<String, List<FlowRule>> parser = source -> JSON.parseObject(source,
                new TypeReference<List<FlowRule>>() {});
        ReadableDataSource<String, List<FlowRule>> nacosDataSource =
                    new NacosDataSource<>(remoteAddress, groupId, dataId, parser);
        FlowRuleManager.register2Property(nacosDataSource.getProperty());
    }
}
