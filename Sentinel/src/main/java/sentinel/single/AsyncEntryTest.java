package sentinel.single;
import com.alibaba.csp.sentinel.*;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/17
 * Time: 8:01 PM
 * Description: No Description
 */
public class AsyncEntryTest {
    public static String  name = "sourceName";
    public static void main( String[] args ) {
        Entry entry =null;
        Integer userId =1;
        try {
            //Boolean b = SphO.entry("sourceName");

//            entry = SphU.entry("sourceName");
            AsyncEntry asyncEntry =SphU.asyncEntry(name);

            // 异步调用.
            doAsync(userId, result -> {
                try {
                    // 在此处处理异步调用的结果.
                } finally {
                    // 在回调结束后 exit.
                    entry.exit();
                }
            });
        } catch (BlockException e) {

        } finally {
//            SphO.exit();
            if (entry!=null){
                entry.exit();
            }
        }
    }

    private static void doAsync(Integer arg, Consumer<String> handler) {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                String resp = arg + ": " + System.currentTimeMillis();
                handler.accept(resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
