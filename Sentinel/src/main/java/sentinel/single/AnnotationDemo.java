//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import sun.jvm.hotspot.HelloWorld;
//
///**
// * Created with IntelliJ IDEA.
// * User: zhifang.xu
// * Date: 2019/10/18
// * Time: 11:22 PM
// * Description: No Description
// */
//public class AnnotationDemo {
//
//    @SentinelResource("HelloWorld")
//    public static void helloWorld() {
//        // 资源中的逻辑
//        System.out.println("hello world");
//    }
//
//    /***
//     * Sentinel 支持通过 @SentinelResource
//     * 注解定义资源并配置 blockHandler 和 fallback 函数来进行限流之后的处理
//     * @param id
//     * @return
//     */
//    // 原本的业务方法.
//    @SentinelResource(blockHandler = "blockHandlerForGetUser")
//    public User getUserById(String id) {
//        throw new RuntimeException("getUserById command failed");
//    }
//
//    // blockHandler 函数，原方法调用被限流/降级/系统保护的时候调用
//    public User blockHandlerForGetUser(String id, BlockException ex) {
//        return new User("admin");
//    }
//}
