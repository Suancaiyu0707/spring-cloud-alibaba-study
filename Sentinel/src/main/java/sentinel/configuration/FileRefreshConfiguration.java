package sentinel.configuration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.FileRefreshableDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 11:20 AM
 * Description: No Description
 */
@Configuration
public class FileRefreshConfiguration {
    static{
        init();
    }
    private static void init() {
        try{
            // 保存了限流规则的文件的地址
            String flowRuleName = "/FlowRule.json";
            //定义一个转换器，用于将存储源中的string转换成目标类型
            Converter<String,List<FlowRule>> converter =source->
                    JSON.parseObject(source,new com.alibaba.fastjson.TypeReference<List<FlowRule>>(){});
            //创建文件规则数据源，用于读取指定目录下的文件，然后交由parser进行转换
            FileRefreshableDataSource<List<FlowRule>> flowRuleDataSource =
                    new FileRefreshableDataSource<>(flowRuleName, converter);
            // 将Property注册到 RuleManager 中去
            FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
