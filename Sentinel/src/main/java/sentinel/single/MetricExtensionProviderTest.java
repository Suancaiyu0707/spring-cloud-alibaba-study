package sentinel.single;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.metric.extension.MetricExtension;
import com.alibaba.csp.sentinel.metric.extension.MetricExtensionProvider;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.statistic.StatisticSlotCallbackRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/30
 * Time: 2:19 PM
 * Description: No Description
 */
public class MetricExtensionProviderTest {
    public static void main( String[] args ) {
        StatisticSlotCallbackRegistry.addEntryCallback("hi", new ProcessorSlotEntryCallback <DefaultNode>() {
            @Override
            public void onPass( Context context, ResourceWrapper resourceWrapper, DefaultNode defaultNode, int i, Object... objects ) throws Exception {

            }

            @Override
            public void onBlocked( BlockException e, Context context, ResourceWrapper resourceWrapper, DefaultNode defaultNode, int i, Object... objects ) {

            }
        });

        MetricExtensionProvider.addMetricExtension(new MetricExtension() {
            @Override
            public void addPass( String s, int i, Object... objects ) {

            }

            @Override
            public void addBlock( String s, int i, String s1, BlockException e, Object... objects ) {

            }

            @Override
            public void addSuccess( String s, int i, Object... objects ) {

            }

            @Override
            public void addException( String s, int i, Throwable throwable ) {

            }

            @Override
            public void addRt( String s, long l, Object... objects ) {

            }

            @Override
            public void increaseThreadNum( String s, Object... objects ) {

            }

            @Override
            public void decreaseThreadNum( String s, Object... objects ) {

            }
        });
    }
}
