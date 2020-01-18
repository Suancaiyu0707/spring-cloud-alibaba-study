package sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/24
 * Time: 9:40 AM
 * Description: No Description
 */
@RestController
@RequestMapping("/core")
public class TestCoreSentinelController {
    public String testFlowRule(){
        ContextUtil.enter("coreSentinle_text");
        Entry entry = null;
        String ss="";
        try {
            entry= SphU.entry("createOrder");
            ss="获得资源成功";
        } catch (BlockException e) {
            e.printStackTrace();
            ss="获得资源失败";
        }finally {
            if(entry!=null){
                entry.exit();
            }
        }
        return ss;
    }


}
